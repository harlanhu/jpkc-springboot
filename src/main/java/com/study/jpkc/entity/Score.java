package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

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
@TableName("t_score")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String scoreId;

    private String userId;

    private String courseId;

    private Double mark;

    private Double schedule;

    private LocalDate createTime;
}
