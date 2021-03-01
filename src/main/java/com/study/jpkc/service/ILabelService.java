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
}
