package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String studentId;

    private String userId;

    private String schoolId;

    private String majorId;

    private String classId;

    private String studentNo;

    private String studentName;


}
