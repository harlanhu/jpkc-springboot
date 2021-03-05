package com.study.jpkc.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.study.jpkc.entity.Category;
import com.study.jpkc.entity.Label;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/3/1 21:51
 * @desc 课程详情Dto
 */
@Data
public class CourseDetailsDto implements Serializable {

    private String courseId;

    private String teacherId;

    private String courseName;

    private String courseDesc;

    private String courseLogo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime courseCreated;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime courseUpdated;

    private Integer sectionCount;

    private Integer courseStar;

    private Integer courseViews;

    private Integer courseHour;

    private Integer courseStatus;

    private BigDecimal coursePrice;

    private List<Category> categoryList;

    private List<Label> labelList;

    private List<SectionDetailDto> sectionDetailDtoList;
}
