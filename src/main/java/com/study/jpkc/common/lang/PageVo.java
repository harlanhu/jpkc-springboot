package com.study.jpkc.common.lang;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Harlan
 * @Date 2021/1/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo implements Serializable {

    private List<?> list;
    private Long current;
    private Long size;
    private Long total;
    private Long pages;

    public PageVo(IPage<?> pageData) {
        this.list = pageData.getRecords();
        this.current = pageData.getCurrent();
        this.size = pageData.getSize();
        this.pages = pageData.getPages();
        this.total = pageData.getTotal();
    }

    public static PageVo getPageVo(IPage<?> pageData) {
        return new PageVo(pageData);
    }

    public static Result getResult(IPage<?> pageData) {
        return Result.getSuccessRes(getPageVo(pageData));
    }
}
