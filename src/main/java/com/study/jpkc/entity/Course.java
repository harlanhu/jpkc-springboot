package com.study.jpkc.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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


}
