package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.School;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface ISchoolService extends IService<School> {

    /**
     * 通过教师id获取学校
     * @param teacherId 教师id
     * @return 学校信息
     */
    School getByTeacherId(String teacherId);

    /**
     * 通过课程id获取学校
     * @param courseId 课程id
     * @return 学校信息
     */
    School getByCourseId(String courseId);

    /**
     * 通过资源id获取学校名称
     * @param resourceId 资源id
     * @return 学校名称
     */
    String getNameByResourceId(String resourceId);

    /**
     * 获取布局学校
     * @param layoutId 布局id
     * @return 学校
     */
    List<School> getSchoolByLayout(String layoutId);

    /**
     * 获取未绑定布局的学校
     * @param layoutId 布局id
     * @param current 当前页
     * @param size 每页大小
     * @return 学校信息
     */
    Page<School> getAllWithoutLayout(String layoutId, Integer current, Integer size);
}
