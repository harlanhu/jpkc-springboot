package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.Label;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface ILabelService extends IService<Label> {

    /**
     * 通过课程id查询标签
     * @param courseId 课程id
     * @return 标签
     */
    List<Label> getByCourseId(String courseId);

    /**
     * 批量保存标签
     * @param labelName 标签名称
     * @return id数组
     */
    List<Label> saveLabels(String... labelName);

    /**
     * 课程绑定标签
     * @param courseId 课程id
     * @param labels 标签
     */
    void bindLabelsToCourse(String courseId, List<Label> labels);

    /**
     * 课程解绑标签
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean unbindCourses(String courseId);
}
