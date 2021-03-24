package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.Category;
import com.study.jpkc.mapper.CategoryMapper;
import com.study.jpkc.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAllCategories() {
        return categoryMapper.selectAllCategories();
    }

    @Override
    public List<Category> findRootCategories() {
        return categoryMapper.selectRootCategories();
    }

    @Override
    public List<Category> getBranchesByCategoryId(String categoryId) {
        return categoryMapper.selectBranchesByCategoryId(categoryId);
    }

    @Override
    public List<Category> getByCourseId(String courseId) {
        return categoryMapper.selectByCourseId(courseId);
    }

    @Override
    public void bindCourse(String courseId, Category category) {
        categoryMapper.bindCategoryToCourse(UUID.randomUUID().toString().replace("-", ""), courseId, category);
    }

    @Override
    public void bindCourse(String courseId, String categoryId) {
        categoryMapper.bindCategoryToCourseById(UUID.randomUUID().toString().replace("-", ""), courseId, categoryId);
    }

    @Override
    public int unbindCourse(String courseId) {
        categoryMapper.deleteBindCourse(courseId);
        return 0;
    }
}
