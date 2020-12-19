package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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

    @TableId
    private String classId;

    private String schoolId;

    private String majorId;

    private String classNo;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate classGrade;


}
