package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.jpkc.entity.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface LabelMapper extends BaseMapper<Label> {

    /**
     * 查询所有标签
     * @return 所有标签
     */
    @Select("select * from t_label")
    List<Label> selectAll();

    /**
     * 通过课程id查询标签
     * @param courseId 课程id
     * @return 标签
     */
    List<Label> selectByCourseId(String courseId);

    /**
     * 课程绑定标签
     * @param innerId id
     * @param courseId 课程id
     * @param label 标签
     * @return 影响行数
     */
    int bindLabelToCourse(@Param("innerId") String innerId, @Param("courseId") String courseId, @Param("label") Label label);

    /**
     * 课程解绑id
     * @param courseId 课程id
     * @return 影响行数
     */
    int unbindCourses(String courseId);
}
