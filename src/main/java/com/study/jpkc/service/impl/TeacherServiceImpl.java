package com.study.jpkc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.study.jpkc.common.component.OssComponent;
import com.study.jpkc.common.constant.OssConstant;
import com.study.jpkc.entity.Teacher;
import com.study.jpkc.mapper.TeacherMapper;
import com.study.jpkc.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.utils.FileUtils;
import com.study.jpkc.utils.GenerateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    private final TeacherMapper teacherMapper;

    private final OssComponent ossComponent;

    public TeacherServiceImpl(TeacherMapper teacherMapper, OssComponent ossComponent) {
        this.teacherMapper = teacherMapper;
        this.ossComponent = ossComponent;
    }

    @Override
    public Teacher getByCourseId(String courseId) {
        return teacherMapper.selectOneByCourseId(courseId);
    }

    @Override
    public String save(Teacher teacher, MultipartFile avatarFile) throws IOException {
        String teacherId = GenerateUtils.getUUID();
        teacher.setTeacherId(teacherId);
        teacher.setTeacherAvatar("https://web-applications.oss-cn-chengdu.aliyuncs.com/jpck/teacher/default/avatar/teacher-default-avatar.png");
        if (teacherMapper.insert(teacher) == 1) {
            ossComponent.upload(OssConstant.TEACHER_PATH + teacherId + "/readme.txt", JSON.toJSONString(teacher));
            if (ObjectUtil.isNotNull(avatarFile)) {
                uploadAvatar(teacherId, avatarFile);
            }
            return teacherId;
        }
        return null;
    }

    @Override
    public boolean uploadAvatar(String teacherId, MultipartFile avatarFile) throws IOException {
        if (avatarFile.getOriginalFilename() != null) {
            URL url = ossComponent.upload(OssConstant.TEACHER_PATH + teacherId + "/avatar/" + FileUtils.getFileSuffix(avatarFile.getOriginalFilename()), avatarFile.getBytes());
            Teacher teacher = new Teacher();
            teacher.setTeacherId(teacherId);
            teacher.setTeacherAvatar(url.toString());
            return teacherMapper.updateById(teacher) == 1;
        } else {
            return false;
        }
    }
}
