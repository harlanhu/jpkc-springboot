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
@TableName("t_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String teacherId;

    private String userId;

    private String schoolId;

    private String teacherNo;

    private String teacherName;

    private String teacherAvatar;

    private String teacherDesc;

}
