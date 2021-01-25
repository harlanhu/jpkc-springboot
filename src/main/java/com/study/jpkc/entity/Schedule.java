package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("t_schedule")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String scheduleId;

    private String classId;

    private String userId;

    private String sectionId;

    private Double schedule;
}
