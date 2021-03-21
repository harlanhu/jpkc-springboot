package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.LiveCourse;
import com.study.jpkc.mapper.LiveCourseMapper;
import com.study.jpkc.service.ILiveCourseService;
import com.study.jpkc.utils.GenerateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-02-05
 */
@Service
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements ILiveCourseService {

    private final LiveCourseMapper liveCourseMapper;

    public LiveCourseServiceImpl(LiveCourseMapper liveCourseMapper) {
        this.liveCourseMapper = liveCourseMapper;
    }

    @Override
    public Page<LiveCourse> getLiveCourse(Integer current, Integer size) {
        return liveCourseMapper.selectPage(new Page<>(current, size), new QueryWrapper<LiveCourse>().orderBy(true,true,"reserve_time"));
    }

    @Override
    public List<LiveCourse> getByUserId(String userId) {
        return liveCourseMapper.selectByUserId(userId);
    }

    @Override
    public boolean save(String teacherId, LiveCourse lCourse) {
        lCourse.setTeacherId(teacherId);
        lCourse.setCreated(LocalDateTime.now());
        lCourse.setStar(0);
        lCourse.setLiveCourseId(GenerateUtils.getUUID());
        return liveCourseMapper.insert(lCourse) == 1;
    }
}
