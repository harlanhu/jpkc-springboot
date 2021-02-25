package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_live_course")
public class LiveCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String liveCourseId;

    private String teacherId;

    private LocalDateTime created;

    private LocalDateTime reserveTime;

    private String title;

    private Integer starts;

    private LocalDateTime finished;


}
