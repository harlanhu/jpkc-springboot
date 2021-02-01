package com.study.jpkc.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Harlan
 * @Date 2021/2/1
 */
@SpringBootTest
public class ScheduleTaskTest {

    @Autowired
    ScheduleTask scheduleTask;

    @Test
    void courseWeekTopTaskTest() {
        scheduleTask.courseWeekTopTask();
    }
}
