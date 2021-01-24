package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.jpkc.entity.Course;
import org.apache.ibatis.annotations.Mapper;

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
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 查询所有课程
     * @return 所有课程
     */
    List<Course> selectAllCourse();


    /**
     * 通过userId查询用户订阅课程
     * @param userId 用户id
     * @return 订阅课程
     */
    List<Course> selectCourseByUserId(String userId);

}
