package com.example.demo.Config;

import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.example.demo.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Component
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InputException.class)
    public Result validExceptionHandler(HttpServletRequest request, InputException ex) {
        log.info("ex:{}",ex);
        return Result.response(HttpStatus.FAILED, HttpMessage.SYSTEM_ERROR,ex.getErrorMessage());
    }


}
