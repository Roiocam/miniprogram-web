package com.fangdushuzi.web.utils;

import com.fangdushuzi.web.enums.ImagePath;
import com.fangdushuzi.web.enums.PathEnum;
import com.fangdushuzi.web.to.FileDelete;
import com.fangdushuzi.web.to.FileResult;
import com.fangdushuzi.web.to.Token;
import com.fangdushuzi.web.to.FileUpload;
import com.fangdushuzi.web.to.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andy Chen
 * @date 2020/5/14 下午12:57
 */
@Slf4j
public class CloudFileUtils {
    public static FileResult deleteImage(String... list){
        Token token = TokenUtils.getToken();
        Mono<FileResult> resp = WebClient.create()
                                         .post()
                                         .uri(uriBuilder -> uriBuilder
                                                 .scheme(Constant.SCHEME)
                                                 .host(Constant.HOST)
                                                 .path(PathEnum.DELETE_URL.getPath())
                                                 .queryParam("access_token", token.getAccess_token())
                                                 .build())
                                         .contentType(MediaType.APPLICATION_JSON_UTF8)
                                         .body(Mono.just(FileDelete.getInstance(list)), FileDelete.class)
                                         .retrieve().bodyToMono(FileResult.class);
        log.info("删除图片:{}",JSONUtils.toJSONString(resp));
        return resp.block();
    }
    public static UploadResult uploadImage(ImagePath imagePath, HttpServletRequest request, String... parameters) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        int modCount = 0;
        Map<String, String> map = new HashMap<>();
        for (String param : parameters) {
            MultipartFile file = multipartHttpServletRequest.getFile(param);
            if (null == file || 0 == file.getSize()){
                continue;
            }
            String filePath;
            if (null != request.getParameter("id")){
                String id = request.getParameter("id");
                filePath = imagePath.getPath().concat(id).concat(Constant.DELIMITER_UNDERLINE).concat(param).concat(Constant.PNG_SUFFIX);
            }else{
                filePath = imagePath.getPath().concat(file.getOriginalFilename());
            }
            FileResult result = CloudFileUtils.getUploadUrl(filePath);
            boolean uploadFile = CloudFileUtils.uploadFile(file.getResource(), filePath, result);
            if (uploadFile) {
                map.put(param, result.getFile_id());
                modCount++;
                log.info("上传图片成功:链接:{}",result.getFile_id());
            }
        }
        if (modCount != 0) {
            return UploadResult.getInstance(map);
        } else {
            return UploadResult.getInstance("上传失败");
        }
    }

    /**
     * 获取上传链接
     *
     * @param filePath
     * @return
     */
    private static FileResult getUploadUrl(String filePath) {
        Token token = TokenUtils.getToken();
        Mono<FileResult> resp = WebClient.create()
                                         .post()
                                         .uri(uriBuilder -> uriBuilder
                                                 .scheme(Constant.SCHEME)
                                                 .host(Constant.HOST)
                                                 .path(PathEnum.UPLOAD_URL.getPath())
                                                 .queryParam("access_token", token.getAccess_token())
                                                 .build())
                                         .contentType(MediaType.APPLICATION_JSON_UTF8)
                                         .body(Mono.just(FileUpload.getInstance(filePath)), FileUpload.class)
                                         .retrieve().bodyToMono(FileResult.class);
        return resp.block();
    }

    private static boolean uploadFile(Resource file, String filePath, FileResult result) {
        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            RestTemplate restTemplate = restTemplateBuilder.build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
            form.add("key", filePath);
            form.add("Signature", result.getAuthorization());
            form.add("x-cos-security-token", result.getToken());
            form.add("x-cos-meta-fileid", result.getCos_file_id());
            form.add("file", file);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(form, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(result.getUrl(), request, String.class);
            return true;
        } catch (Exception e) {
            log.error("上传图片失败:{}",e);
            return false;
        }
    }
}
