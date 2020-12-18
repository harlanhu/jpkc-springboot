package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
@TableName("t_school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    private String schoolId;

    private String schoolName;

    private String schoolAlias;

    private String schoolDesc;

    private LocalDate schoolCreated;

    private LocalDateTime schoolUpdate;

    private String schoolLogo;

    private String schoolAddress;

    private String schoolPhone;

    private Integer schoolType;


}
