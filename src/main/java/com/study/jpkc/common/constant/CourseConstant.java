package com.study.jpkc.common.constant;

/**
 * @Author Harlan
 * @Date 2021/3/23
 */
public final class CourseConstant {

    private CourseConstant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String COL_ID = "course_id";

    public static final String COL_STATUS = "course_status";

    public static final String COL_PRICE = "course_price";

    public static final String COL_VIEWS = "course_views";

    public static final String COL_STAR = "course_star";

    public static final String COL_SCHOOL_ID ="school_id";

    public static final String COL_NAME = "course_name";

    public static final String COL_DESC = "course_desc";

    public static final String ES_INDEX = "course";

    public static final int COURSE_ALL = 0;

    public static final int COURSE_POPULAR = 1;

    public static final int COURSE_COLLECT = 2;

    public static final int COURSE_FREE = 3;

    public static final int COURSE_CHARGE = 4;
}
