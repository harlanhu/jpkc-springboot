package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("t_section")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String sectionId;

    private String courseId;

    private String sectionName;

    private String parentId;

    private Integer sectionNo;

    private Integer resourceCount;

    private String sectionDesc;


}
