package com.example.demo.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {



    private int code;

    private String msg = "success";


    private T data;

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> Result<T> response(int code, String msg,T data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
