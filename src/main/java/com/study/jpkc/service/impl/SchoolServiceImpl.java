package com.study.jpkc.service.impl;

import com.study.jpkc.entity.School;
import com.study.jpkc.mapper.SchoolMapper;
import com.study.jpkc.service.ISchoolService;
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
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }

    @Override
    public School getByTeacherId(String teacherId) {
        return schoolMapper.selectByTeacherId(teacherId);
    }

    @Override
    public School getByCourseId(String courseId) {
        return schoolMapper.selectByCourseId(courseId);
    }

    @Override
    public String getNameByResourceId(String resourceId) {
        return schoolMapper.selectNameByResourceId(resourceId);
    }
}
