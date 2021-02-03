package com.study.jpkc.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.entity.Course;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.utils.RedisUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2021/2/1
 * @Desc 定时任务处理
 */
@Service
public class CourseScheduleTask {

    private final ICourseService courseService;

    private final RedisUtils redisUtils;

    public static final String COURSE_TOP_50_KEY = "courseWeekTop";

    public static final String COURSE_NEW_KEY = "courseWeekNew";

    public static final String COURSE_STAR_KEY = "courseWeekStar";

    private static final String RANKING_SIZE = "limit 50";

    public CourseScheduleTask(ICourseService courseService, RedisUtils redisUtils) {
        this.courseService = courseService;
        this.redisUtils = redisUtils;
    }

    @Scheduled(cron = "59 59 23 * * SUN")
    public void courseWeekTopTask() {
        if (redisUtils.hasKey(COURSE_TOP_50_KEY)) {
            redisUtils.del(COURSE_TOP_50_KEY);
        }
        List<Course> courseList = courseService.list(new QueryWrapper<Course>()
                .orderBy(true, false, "course_views")
                .last(RANKING_SIZE));
        courseList.forEach(item -> redisUtils.setListItem(COURSE_TOP_50_KEY, item));
    }

    @Scheduled(cron = "59 59 23 * * SUN")
    public void courseNewTask() {
        if (redisUtils.hasKey(COURSE_NEW_KEY)) {
            redisUtils.del(COURSE_NEW_KEY);
        }
        List<Course> courseList = courseService.list(new QueryWrapper<Course>()
        .orderBy(true, false, "course_created")
        .last(RANKING_SIZE));
        courseList.forEach(item -> redisUtils.setListItem(COURSE_NEW_KEY, item));
    }

    @Scheduled(cron = "59 59 23 * * SUN")
    public void courseStarTask() {
        if (redisUtils.hasKey(COURSE_STAR_KEY)) {
            redisUtils.del(COURSE_STAR_KEY);
        }
        List<Course> courseList = courseService.list(new QueryWrapper<Course>()
                .orderBy(true, false, "course_star")
                .last(RANKING_SIZE));
        courseList.forEach(item -> redisUtils.setListItem(COURSE_STAR_KEY, item));
    }
}
