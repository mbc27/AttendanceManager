package com.rabbiter.am.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        // 自定义异常处理逻辑
        String message = e.getMessage();
        e.printStackTrace();
        
        // 防止空指针
        if (message == null) {
            if (e instanceof NullPointerException) {
                return new ResponseEntity<>("系统处理出错：空指针异常，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<>("系统处理出错，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        if (message.contains("(using password: YES)")) {
            if (!message.contains("'root'@'")) {
                message = "PU Request failed with status code 500";
            } else if (message.contains("'root'@'localhost'")) {
                message = "P Request failed with status code 500";
            }
        } else if(message.contains("Table") && message.contains("doesn't exist")) {
            message = "T Request failed with status code 500";
        } else if (message.contains("Unknown database")) {
            message = "U Request failed with status code 500";
        } else if (message.contains("edis")) {
            message = "R Request failed with status code 500";
        } else if (message.contains("Failed to obtain JDBC Connection")) {
            message = "C Request failed with status code 500";
        } else if (message.contains("SQLSyntaxErrorException")) {
            message = "S Request failed with status code 500";
        } else if (message.contains("TooManyResultsException")) {
            message = "查询结果过多，请联系系统管理员修复";
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
