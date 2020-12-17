package com.study.jpkc.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Harlan
 * @Date 2020/12/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultBean implements Serializable {

    private Integer resultCoed;
    private Object resultContent;
    private String resultDesc;

    public static ResultBean getSuccessRes(Object resultContent, String resultDesc) {
        return new ResultBean(200, resultContent, resultDesc);
    }

    public static ResultBean getSuccessRes(Object resultContent) {
        return new ResultBean(200, resultContent, "操作成功");
    }

    public static ResultBean getFailRes() {
        return new ResultBean(400, null, "操作失败");
    }

    public static ResultBean getFailRes(String resultDesc) {
        return new ResultBean(400, null, resultDesc);
    }
}
