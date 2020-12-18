package com.study.jpkc.entity;

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
@TableName("t_class")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    private String classId;

    private String schoolId;

    private String majorId;

    private String classNo;

    private LocalDate classGrade;


}
