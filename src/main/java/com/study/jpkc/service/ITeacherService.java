package com.study.jpkc.service;

import com.study.jpkc.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface ITeacherService extends IService<Teacher> {


    /**
     * 通过课程id获取教师信息
     * @param courseId 课程id
     * @return teacherInfo
     */
    Teacher getOneByCourseId(String courseId);
}
