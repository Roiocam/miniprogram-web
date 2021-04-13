package com.fangdushuzi.web.to;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/14 下午1:03
 */
@Data
public class FileDelete {
    private String env = "fangdu-y00yc";
    private List<String> fileid_list;

    public static FileDelete getInstance(String... fileIds){
        List<String> list = new ArrayList<>();
        FileDelete delete = new FileDelete();
        for(String id:fileIds){
            list.add(id);
        }
        delete.setFileid_list(list);
        return delete;
    }
}
