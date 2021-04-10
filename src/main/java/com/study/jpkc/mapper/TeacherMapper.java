package com.study.jpkc.mapper;

import com.study.jpkc.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {


    /**
     * 通过课程id查询教师信息
     * @param courseId 课程id
     * @return teacherInfo
     */
    Teacher selectOneByCourseId(String courseId);

    /**
     * 通过直播id查询教师
     * @param lCourseId 直播id
     * @return 教师信息
     */
    Teacher selectByLCourseId(String lCourseId);

}
