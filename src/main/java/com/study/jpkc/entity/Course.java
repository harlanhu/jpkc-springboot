package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private String courseId;

    private String courseName;

    private String courseDesc;

    private String courseLogo;

    private LocalDateTime courseCreated;

    private LocalDateTime courseUpdated;

    private Integer sectionCount;

    private String courseStar;

    private String courseViews;

    private Integer courseHour;

    private BigDecimal coursePrice;

    private Integer courseStatus;

    public String getCourseStatus() {
        if (courseStatus == 0) {
            return "开放";
        }
        if (courseStatus == 1) {
            return "关闭";
        }
        return null;
    }
}
