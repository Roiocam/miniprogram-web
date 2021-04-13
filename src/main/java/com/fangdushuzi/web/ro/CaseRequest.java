package com.fangdushuzi.web.ro;

import com.fangdushuzi.web.entity.Case;
import com.fangdushuzi.web.utils.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author Andy Chen
 * @date 2020/5/14 上午8:46
 */
@Data
public class CaseRequest implements Parse<Case> {
    @NotBlank
    private String id;
    private int function;
    private int style;
    private int tabs;
    @NotBlank
    private String title;
    private String previewUrl;
    private List<String> vrList;


    @Override
    public Case parse(Case obj) {
        obj.setId(Integer.parseInt(id));
        obj.setTitle(title);
        obj.setStyle(style);
        obj.setFunction(function);
        if (previewUrl != null) obj.setPreview(previewUrl);
        if (vrList != null && vrList.size() != 0){
            obj.setVr(vrList);
        }
        return obj;
    }

    @Override
    public Case parse() {
        Case aCase = new Case();
        aCase.setFunction(function);
        aCase.setId(Integer.parseInt(id));
        aCase.setStyle(style);
        aCase.setTitle(title);
        if (previewUrl != null) aCase.setPreview(previewUrl);
        if (vrList != null && vrList.size() != 0){
            aCase.setVr(vrList);
        }
        aCase.setTabs(tabs);
        return aCase;
    }
}

