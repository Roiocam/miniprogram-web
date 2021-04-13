package com.fangdushuzi.web.ro;

import com.fangdushuzi.web.entity.Function;
import com.fangdushuzi.web.utils.NotBlank;
import lombok.Data;

/**
 * @author Andy Chen
 * @date 2020/5/14 上午10:31
 */
@Data
public class FunctionRequest implements Parse<Function> {
    @NotBlank
    private String id;
    @NotBlank
    private String function;


    @Override
    public Function parse(Function obj) {
        obj.setFunction(function);
        obj.setId(Integer.parseInt(id));
        return obj;
    }

    @Override
    public Function parse() {
        Function function = new Function();
        function.setId(Integer.parseInt(id));
        function.setFunction(this.function);
        return function;
    }
}
