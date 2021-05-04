package com.study.jpkc.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/5/4 14:47
 * @desc
 */
@Data
public class AnswerDto implements Serializable {

    private String courseId;

    private List<Integer> answers;
}
