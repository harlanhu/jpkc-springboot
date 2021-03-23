package com.study.jpkc.common.constant;

/**
 * @Author Harlan
 * @Date 2021/3/23
 */
public final class CourseConstant {

    private CourseConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final int COURSE_ALL = 0;

    public static final int COURSE_POPULAR = 1;

    public static final int COURSE_COLLECT = 2;

    public static final int COURSE_FREE = 3;

    public static final int COURSE_CHARGE = 4;
}
