package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_exam")
@AllArgsConstructor
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("courseId")
    private String courseId;

    private String topic;

    private String options;

    private Integer answer;

    private Integer value;

    private Integer no;


}
