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

    /**
     * 通过类别id获取枝叶类别
     * @param categoryId 类别id
     * @return 枝叶类别
     */
    List<Category> getBranchesByCategoryId(String categoryId);

    /**
     * 通过课程id获取类别
     * @param courseId 课程id
     * @return 类别
     */
    List<Category> getByCourseId(String courseId);
}
