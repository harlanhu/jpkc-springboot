package com.study.jpkc.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.Course;
import com.study.jpkc.entity.Teacher;
import com.study.jpkc.mapper.CourseMapper;
import com.study.jpkc.mapper.TeacherMapper;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.task.CourseScheduleTask;
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

    private final TeacherMapper teacherMapper;

    private final RedisUtils redisUtils;

    public CourseServiceImpl(CourseMapper courseMapper, RedisUtils redisUtils, TeacherMapper teacherMapper) {
        this.courseMapper = courseMapper;
        this.redisUtils = redisUtils;
        this.teacherMapper = teacherMapper;
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
    public List<Course> getRanking(Integer current, Integer size) {
        int start = (current - 1) * size;
        int end = current * size - 1;
        if (end > redisUtils.getListLength(CourseScheduleTask.COURSE_TOP_50_KEY)) {
            end = -1;
        }
        return redisUtils.getList(CourseScheduleTask.COURSE_TOP_50_KEY, start, end)
                .stream().map(item -> (Course)item).collect(Collectors.toList());
    }

    @Override
    public List<Course> getNew(Integer current, Integer size) {
        int start = (current - 1) * size;
        int end = current * size - 1;
        if (end > redisUtils.getListLength(CourseScheduleTask.COURSE_NEW_KEY)) {
            end = -1;
        }
        return redisUtils.getList(CourseScheduleTask.COURSE_NEW_KEY, start, end)
                .stream().map(item -> (Course)item).collect(Collectors.toList());
    }

    @Override
    public List<Course> getStar(Integer current, Integer size) {
        int start = (current - 1) * size;
        int end = current * size - 1;
        if (end > redisUtils.getListLength(CourseScheduleTask.COURSE_STAR_KEY)) {
            end = -1;
        }
        return redisUtils.getList(CourseScheduleTask.COURSE_STAR_KEY, start, end)
                .stream().map(item -> (Course)item).collect(Collectors.toList());
    }

    @Override
    public Page<Course> getByLabelId(String labelId, Integer current, Integer size) {
        return courseMapper.selectByLabelId(new Page<>(current, size), labelId);
    }

    @Override
    public Page<Course> getByCategoryId(String categoryId, Integer current, Integer size) {
        return courseMapper.selectByCategoryId(new Page<>(current, size), categoryId);
    }

    @Override
    public Boolean create(AccountProfile accountProfile, Course course) {
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("user_id", accountProfile.getUserId()));
        course.setTeacherId(teacher.getTeacherId());
        course.setSchoolId(teacher.getSchoolId());
        course.setCourseId(UUID.randomUUID().toString().replace("-", ""));
        return courseMapper.insert(course) == 1;
    }
}
