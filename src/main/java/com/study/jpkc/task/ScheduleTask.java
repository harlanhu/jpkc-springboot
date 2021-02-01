package com.study.jpkc.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.entity.Course;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author Harlan
 * @Date 2021/2/1
 * @Desc 定时任务处理
 */
@Service
public class ScheduleTask {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(cron = "59 59 23 * * SUN")
    public void courseWeekTopTask() {
        List<Course> courseList = courseService.list(new QueryWrapper<Course>().orderBy(true, false, "course_views"));
        redisUtils.setList("courseWeekTop", Collections.singletonList(courseList));
    }
}
