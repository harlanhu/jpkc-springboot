package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.LiveCourse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-02-05
 */
public interface ILiveCourseService extends IService<LiveCourse> {

    /**
     * 分页获取直播课程
     * @param current 当前页
     * @param size 每页大小
     * @return list
     */
    Page<LiveCourse> getLiveCourse(Integer current, Integer size);
}
