package com.study.jpkc.common.dto;

import com.study.jpkc.entity.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Harlan
 * @Date 2021/3/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto implements Serializable {

    private String teacherId;

    private String userId;

    private String schoolId;

    private String teacherNo;

    private String teacherName;

    private String teacherAvatar;

    private String teacherDesc;

    private String teacherPost;

    private School school;

}
