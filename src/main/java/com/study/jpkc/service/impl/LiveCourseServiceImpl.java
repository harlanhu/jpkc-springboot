package com.study.jpkc.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
        String lCourseId = GenerateUtils.getUUID();
        lCourse.setUrl("http://47.108.151.199:8080/hls/" + lCourseId + ".m3u8");
        lCourse.setTeacherId(teacherId);
        lCourse.setCreated(LocalDateTime.now());
        lCourse.setStar(0);
        lCourse.setLiveCourseId(lCourseId);
        return liveCourseMapper.insert(lCourse) == 1;
    }

    @Override
    public boolean addViews(String lCourseId) {
        LiveCourse lCourse = liveCourseMapper.selectById(lCourseId);
        if (ObjectUtil.isNotNull(lCourse)) {
            lCourse.setViews(lCourse.getViews() + 1);
            return liveCourseMapper.updateById(lCourse) == 1;
        }
        return false;
    }

    @Override
    public boolean addStar(String lCourseId) {
        LiveCourse lCourse = liveCourseMapper.selectById(lCourseId);
        if (ObjectUtil.isNotNull(lCourse)) {
            lCourse.setStar(lCourse.getStar() + 1);
            return liveCourseMapper.updateById(lCourse) == 1;
        }
        return false;
    }
}
