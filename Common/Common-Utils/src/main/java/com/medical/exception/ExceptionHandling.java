package com.medical.exception;

import com.medical.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Luo.X
 * @Description 全局异常处理类
 * @CreateTime 2022-09-06 16:46
 * @Version 1.0
 */
@ControllerAdvice
public class ExceptionHandling {


    // @ExceptionHandler(Exception.class)设置什么异常执行
    @ExceptionHandler(Exception.class)
    // 将结果用json格式输出
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    // @ExceptionHandler(customizeException.class)设置什么异常执行
    @ExceptionHandler(customizeException.class)
    // 将结果用json格式输出
    @ResponseBody
    public Result error(customizeException e){
        e.printStackTrace();
        return Result.fail();
    }
}
