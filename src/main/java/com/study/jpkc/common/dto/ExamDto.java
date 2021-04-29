package com.study.jpkc.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/4/30 3:00
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("courseId")
    private String courseid;

    private String topic;

    private List<String> options;

    private Integer answer;

    private Integer value;

    private Integer no;
}
