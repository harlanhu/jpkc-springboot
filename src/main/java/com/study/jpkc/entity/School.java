package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @TableId
    private String schoolId;

    private String schoolName;

    private String schoolAlias;

    private String schoolDesc;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate schoolCreated;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime schoolUpdate;

    private String schoolLogo;

    private String schoolAddress;

    @NotNull
    private String schoolPhone;

    @NotNull
    private Integer schoolType;

    public String getSchoolType() {
        if (schoolType == 0) {
            return "大学本科";
        }
        if (schoolType == 1) {
            return "大学专科";
        }
        if (schoolType == 3) {
            return "高中院校";
        }
        return null;
    }
}
