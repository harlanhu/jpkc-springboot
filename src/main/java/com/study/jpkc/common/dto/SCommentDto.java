package com.study.jpkc.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Harlan
 * @Date 2021/3/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SCommentDto implements Serializable {

    private String commentId;

    private String sectionId;

    private String userId;

    private String parentId;

    private String commentContent;

    private Integer commentStar;

    private String userName;

    private String userAvatar;
}
