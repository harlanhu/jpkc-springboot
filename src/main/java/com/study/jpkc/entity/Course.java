package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    private static final String COURSE_REGEX = "^[a-z0-9_-]{3,16}$";
    private static final String COURSE_DESC_REGEX = "^[a-z0-9_-]{10,256}$";
    private static final String COURSE_SECTION_COUNT_REGEX = "^[1-9]\\d*$";
    private static final String COURSE_HOUR_REGEX = "^[1-9]\\d*$";
    private static final String COURSE_PRICE_REGEX = "/(^[1-9]\\d*(\\.\\d{1,2})?$)|(^0(\\.\\d{1,2})?$)/";

    @TableId
    private String courseId;

    private String teacherId;

    @NotBlank(message = "课程名不能为空")
    @Pattern(regexp = COURSE_REGEX, message = "课程名格式不正确")
    private String courseName;

    @NotBlank(message = "课程表述不能为空")
    @Pattern(regexp = COURSE_DESC_REGEX, message = "课程描述格式不正确")
    private String courseDesc;

    private String courseLogo;

    private String coursePpt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime courseCreated;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime courseUpdated;

    @NotBlank(message = "课程章节数不能为空")
    @Pattern(regexp = COURSE_SECTION_COUNT_REGEX, message = "课程章节数格式不正确")
    private Integer sectionCount;

    private Integer courseStar;

    private Integer courseViews;

    @NotBlank(message = "学时数不能为空")
    @Pattern(regexp = COURSE_HOUR_REGEX, message = "课程学时数格式不正确")
    private Integer courseHour;

    @NotBlank(message = "价格不能为空")
//    @Pattern(regexp = COURSE_PRICE_REGEX, message = "课程价格格式不正确")
    private BigDecimal coursePrice;

    private Integer courseStatus;

    private String schoolId;
}
