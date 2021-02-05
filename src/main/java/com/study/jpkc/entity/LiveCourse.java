package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    private String liveCourseId;

    private String teacherId;

    private LocalDate created;

    private LocalDate reserveTime;

    private String title;

    private Integer starts;

    private LocalDate finished;


}
