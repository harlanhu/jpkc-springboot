package com.study.jpkc.common.exception;

import lombok.*;

/**
 * 通用异常
 * @Author Harlan
 * @Date 2021/1/4
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CommonException extends RuntimeException{

    private final int code;

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }
}
