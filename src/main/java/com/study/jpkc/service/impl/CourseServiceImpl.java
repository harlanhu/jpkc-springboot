package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.Course;
import com.study.jpkc.mapper.CourseMapper;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    private final CourseMapper courseMapper;

    private final RedisUtils redisUtils;

    public CourseServiceImpl(CourseMapper courseMapper, RedisUtils redisUtils) {
        this.courseMapper = courseMapper;
        this.redisUtils = redisUtils;
    }

    @Override
    public List<Course> selectAllCourse() {
        return courseMapper.selectAllCourse();
    }

    @Override
    public List<Course> findCourseByUserId(String userId) {
        return courseMapper.selectCourseByUserId(userId);
    }

    @Override
    public List<Course> getRanking() {
        return redisUtils.getList("courseWeekTop", 0, -1).stream().map(item -> (Course)item).collect(Collectors.toList());
    }
}
