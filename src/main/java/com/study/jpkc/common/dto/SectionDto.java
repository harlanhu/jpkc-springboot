package com.study.jpkc.common.dto;

import com.study.jpkc.entity.Resource;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Harlan
 * @Date 2021/3/5
 */
@Data
public class SectionDto implements Serializable {

    private String sectionId;

    private String courseId;

    private String sectionName;

    private String parentId;

    private Integer sectionNo;

    private Integer resourceCount;

    private String sectionDesc;

    private List<Resource> resources;
}
