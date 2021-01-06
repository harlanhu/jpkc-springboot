package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.Category;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 查询所有课程类别
     * @return 类别信息
     */
    List<Category> findAllCategories();

    /**
     * 查询课程根类别
     * @return 根类别信息
     */
    List<Category> findRootCategories();
}
