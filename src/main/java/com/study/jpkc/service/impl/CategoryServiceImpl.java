package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.Category;
import com.study.jpkc.mapper.CategoryMapper;
import com.study.jpkc.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
