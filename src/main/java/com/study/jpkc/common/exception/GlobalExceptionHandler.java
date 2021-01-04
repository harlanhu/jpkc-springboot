package com.study.jpkc.common.exception;

import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.study.jpkc.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/12/21
 * @desc 全局异常处理
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ShiroException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result authenticationTest(ShiroException e) {
        log.warn("Shiro警告 ====>> " + e.getMessage());
        return Result.getFailRes(401, e.getMessage());
    }

    /**
     * 处理Assert的异常
     * @param e 异常
     * @return 返回
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result illegalArgumentException(IllegalArgumentException e) {
        log.warn("Assert警告 ====>> " + e.getMessage());
        return Result.getFailRes(e.getMessage());
    }

    /**
     * 数据绑定校验异常
     * @param e 异常
     * @return 返回
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result bindException(BindException e) {
        List<ObjectError> allErrors = e.getAllErrors();
        ObjectError error = allErrors.get(0);
        log.warn("数据绑定校验警告 ====>> " + error.getDefaultMessage());
        return Result.getFailRes(error.getDefaultMessage());
    }

    /**
     * 验证码校验异常
     * @param e 异常
     * @return 返回信息
     */
    @ExceptionHandler(KaptchaException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Result kaptchaException(KaptchaException e) {
        String tip = "验证码校验警告 ====>> ";
        if (e instanceof KaptchaIncorrectException) {
            log.warn(tip + e.getMessage());
            return Result.getFailRes("验证码不正确");
        } else if (e instanceof KaptchaNotFoundException) {
            log.warn(tip + e.getMessage());
            return Result.getFailRes("请先获取验证码");
        } else if (e instanceof KaptchaTimeoutException) {
            log.warn(tip + e.getMessage());
            return Result.getFailRes("验证码过期");
        } else {
            log.warn(tip + e.getMessage());
            return Result.getFailRes("验证码渲染失败");
        }
    }

    /**
     * 运行时异常
     * @param e 异常
     * @return 返回结果
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result runtimeException(RuntimeException e) {
        log.warn("运行时警告 ====>> " + e.getMessage());
        return Result.getFailRes(e.getMessage());
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result httpMessageConversionException(HttpMessageConversionException e) {
        if (e instanceof HttpMessageNotReadableException) {
            log.warn("请求异常 ====>> " + e.getMessage());
            return Result.getFailRes("请求参数异常");
        }
        return Result.getFailRes("请求异常");
    }
}
