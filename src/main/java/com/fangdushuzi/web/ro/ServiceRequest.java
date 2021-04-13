package com.fangdushuzi.web.ro;

import com.fangdushuzi.web.entity.Service;
import com.fangdushuzi.web.utils.NotBlank;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/14 上午8:46
 */
@Data
public class ServiceRequest implements Parse<Service> {
    @NotBlank
    private String id;
    @NotBlank
    private String desc;
    private String imageUrl;
    @NotBlank
    private String title;


    @Override
    public Service parse(Service obj) {
        obj.setTitle(title);
        obj.setDesc(desc);
        obj.setId(Integer.parseInt(id));
        if (imageUrl != null) obj.setImage(imageUrl);
        return obj;
    }

    @Override
    public Service parse() {
        Service service = new Service();
        service.setId(Integer.parseInt(id));
        service.setDesc(desc);
        service.setImage(imageUrl);
        service.setTitle(title);
        return service;
    }
}

