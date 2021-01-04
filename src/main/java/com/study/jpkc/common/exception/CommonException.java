package com.study.jpkc.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 通用异常
 * @Author Harlan
 * @Date 2021/1/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonException extends RuntimeException{
    private Integer code;
    private String message;
}
