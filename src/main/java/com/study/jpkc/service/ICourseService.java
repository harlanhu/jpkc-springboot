package com.study.jpkc.service;

import com.study.jpkc.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface ICourseService extends IService<Course> {

    /**
     * 搜索所有课程
     * @return 所有课程
     */
    List<Course> selectAllCourse();
}
