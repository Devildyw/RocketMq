package com.dyw.rocketmq.handler;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rocketmq.result.Result;

/**
 * @author Devil
 * @create 2022-02-26 15:33
 */
@Log4j2
@RestControllerAdvice
public class Handler {

    @ExceptionHandler(Exception.class)
    public Result doException(Exception e){
        e.printStackTrace();
        log.error("服务器error");
        return Result.fail(-999,"服务器故障请稍后再试");
    }
}
