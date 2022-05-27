package org.example.ruiji_waimai.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result<T> {

    // 返回编码code
    private Integer code;

    //返回信息
    private String message;

    // 数据
    private T data;

    private Map map = new HashMap();

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.setData(object);
        result.setCode(1);
        result.setMessage("success");
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.setMessage(msg);
        result.setCode(0);
        return result;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
