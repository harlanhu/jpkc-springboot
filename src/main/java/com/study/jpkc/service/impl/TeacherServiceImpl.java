package com.study.jpkc.service.impl;

import com.study.jpkc.entity.Teacher;
import com.study.jpkc.mapper.TeacherMapper;
import com.study.jpkc.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public Teacher getOneByCourseId(String courseId) {
        return teacherMapper.selectOneByCourseId(courseId);
    }
}
