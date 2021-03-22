package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_section_comment")
public class SectionComment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commentId;

    private String sectionId;

    private String userId;

    private String parentId;

    private String commentContent;

    private Integer commentStar;

    private LocalDateTime commentTime;


}
