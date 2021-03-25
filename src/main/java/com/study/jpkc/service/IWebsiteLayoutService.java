package com.study.jpkc.service;

import com.study.jpkc.entity.WebsiteLayout;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
public interface IWebsiteLayoutService extends IService<WebsiteLayout> {

    /**
     * 资源绑定布局
     * @param userId 用户id
     * @param layoutId 布局id
     * @param resourceId 资源id
     * @param desc 描述
     * @return 是否成功
     */
    boolean bindResource(String userId, String layoutId, String resourceId, String desc);

    /**
     * 绑定课程到布局
     * @param layoutId 布局id
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean bindCourse(String layoutId, String courseId);

    /**
     * 课程与布局解绑
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean unbindCourse(String courseId);

    /**
     * 绑定学校到布局
     * @param layoutId 布局id
     * @param schoolId 学校id
     * @return 是否成功
     */
    boolean bindSchool(String layoutId, String schoolId);

    /**
     * 布局与课程解绑
     * @param layoutId 布局id
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean unbindCourse(String layoutId, String courseId);

    /**
     * 布局与学校解绑
     * @param layoutId 布局id
     * @param schoolId 学校id
     * @return 是否成功
     */
    boolean unbindSchool(String layoutId, String schoolId);
}
