package com.study.jpkc.task;

import com.study.jpkc.server.CourseTaskServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Harlan
 * @Date 2021/2/1
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourseTaskServerTest {

    @Autowired
    CourseTaskServer courseTaskServer;

    @Test
    void courseWeekTopTaskTest() {
        courseTaskServer.courseWeekTopTask();
    }

    @Test
    void courseNewTaskTest() {
        courseTaskServer.courseNewTask();
    }

    @Test
    void courseStartTaskTest() {
        courseTaskServer.courseStarTask();
    }

    @Test
    void courseAboutTask() {
        courseTaskServer.courseAboutTask();
    }

    @Test
    void clearLCourseTest() {
        courseTaskServer.clearLCourse();
    }
}
