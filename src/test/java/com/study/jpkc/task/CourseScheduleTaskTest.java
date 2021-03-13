package com.study.jpkc.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Harlan
 * @Date 2021/2/1
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseScheduleTaskTest {

    @Autowired
    CourseScheduleTask courseScheduleTask;

    @Test
    void courseWeekTopTaskTest() {
        courseScheduleTask.courseWeekTopTask();
    }

    @Test
    void courseNewTaskTest() {
        courseScheduleTask.courseNewTask();
    }

    @Test
    void courseStartTaskTest() {
        courseScheduleTask.courseStarTask();
    }
}
