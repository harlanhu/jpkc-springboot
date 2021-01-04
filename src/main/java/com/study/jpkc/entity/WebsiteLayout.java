package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 网站布局表
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_website_layout")
public class WebsiteLayout implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String layoutId;

    private String layoutName;

    private Integer layoutType;

    private String layoutPage;

    private String layoutLocation;

    private String layoutDesc;


}
