package com.study.jpkc.service;

import com.study.jpkc.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    Teacher getByCourseId(String courseId);

    /**
     * 保存教师信息
     * @param teacher 教师信息
     * @param avatarFile 头像文件
     * @return 教师id
     */
    String save(Teacher teacher, MultipartFile avatarFile) throws IOException;

    /**
     * 上传头像
     * @param teacherId 教师id
     * @param avatarFile 头像文件
     * @return 是否成功
     */
    boolean uploadAvatar(String teacherId, MultipartFile avatarFile) throws IOException;
}
