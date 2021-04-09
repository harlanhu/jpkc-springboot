package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.LiveCourse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 通过userId查询直播课程
     * @param userId 用户id
     * @return 直播课程
     */
    List<LiveCourse> getByUserId(String userId);

    /**
     * 创建直播课程
     * @param teacherId 教师id
     * @param lCourse 课程信息
     * @param logoFile logo
     * @return 是否成功
     */
    boolean save(String teacherId, LiveCourse lCourse, MultipartFile logoFile);

    /**
     * 通过id增加观看人数
     * @param lCourseId 直播id
     * @return 是否成功
     */
    boolean addViews(String lCourseId);

    /**
     * 通过id增加收藏人数
     * @param lCourseId 直播id
     * @return 是否成功
     */
    boolean addStar(String lCourseId);
}
