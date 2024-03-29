package com.study.jpkc.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回数据格式封装
 * @Author Harlan
 * @Date 2020/12/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {

    private Integer status;
    private Object data;
    private String message;

    /**
     * 获取全参返回数据
     * @param status 状态码
     * @param data 数据
     * @param message 消息
     * @return 返回数据
     */
    public static Result getRes(Integer status, Object data, String message) {
        Result result = new Result();
        result.setStatus(status);
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static Result getSuccessRes(Object data, String message) {
        return getRes(200, data, message);
    }

    public static Result getSuccessRes(Object data) {
        return getSuccessRes(data, "操作成功");
    }

    public static Result getFailRes(Integer status, String message) {
        return getRes(status, null, message);
    }

    public static Result getFailRes(Object data, String message) {
        return getRes(400, data, message);
    }

    public static Result getFailRes(String message) {
        return getFailRes( 400, message);
    }

    public static Result getFailRes() {
        return getFailRes(null, "操作失败");
    }
}
