package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 网站资源表
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_website_resource")
public class WebsiteResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resourceId;

    private String resourceName;

    private Integer resourceType;

    private Integer resourceWeight;

    private String resourceDesc;

    private String resourcePath;


}
