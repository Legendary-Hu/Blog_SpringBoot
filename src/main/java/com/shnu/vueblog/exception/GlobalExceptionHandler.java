package com.shnu.vueblog.exception;/**
 * @author Legendary_Hu
 * @Description:None
 * @date 2022/3/17 16:37
 */

import com.shnu.vueblog.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: vueblog
 *
 * @description: 全局异常捕获
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-17 16:37
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.error("运行时异常-------------{}",e);
        return Result.fail(e.getMessage());
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e){
        log.error("shiro异常-------------{}",e);
        return Result.fail(401,e.getMessage(),null);
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        log.error("实体校验异常-------------{}",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();//获取第一个错误信息
        return Result.fail(401,objectError.getDefaultMessage(),null);
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result handler(HttpMessageNotReadableException e){
        log.error("提交json数据异常-------------{}",e);

        return Result.fail(401,e.getMessage());
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("Assert异常-------------{}",e);

        return Result.fail(401,e.getMessage());
    }
}
