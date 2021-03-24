package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.jpkc.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询所有类别
     * @return 类别信息
     */
    List<Category> selectAllCategories();

    /**
     * 查询根类别
     * @return 类别信息
     */
    List<Category> selectRootCategories();

    /**
     * 通过id查询枝叶
     * @param categoryId 类别id
     * @return 枝叶类别
     */
    List<Category> selectBranchesByCategoryId(String categoryId);


    /**
     * 通过课程id获取；类别
     * @param courseId 课程id
     * @return 类别
     */
    List<Category> selectByCourseId(String courseId);

    /**
     * 课程绑定类别
     * @param innerId id
     * @param courseId 课程id
     * @param category 类别
     * @return 影响行数
     */
    int bindCategoryToCourse(@Param("innerId") String innerId, @Param("courseId") String courseId, @Param("category") Category category);

    /**
     * 课程绑定类别
     * @param innerId id
     * @param courseId 课程id
     * @param categoryId 类别id
     * @return 影响行数
     */
    int bindCategoryToCourseById(@Param("innerId") String innerId, @Param("courseId") String courseId, @Param("categoryId") String categoryId);

    /**
     * 删除课程绑定
     * @param courseId 课程id
     * @return 影响行数
     */
    int deleteBindCourse(String courseId);
}
