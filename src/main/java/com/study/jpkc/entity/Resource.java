package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resourceId;

    private String sectionId;

    private Integer resourceType;

    private String resourcePath;


}
