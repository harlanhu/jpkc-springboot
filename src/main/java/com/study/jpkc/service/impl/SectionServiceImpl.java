package com.study.jpkc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.common.component.OssComponent;
import com.study.jpkc.common.constant.OssConstant;
import com.study.jpkc.entity.Resource;
import com.study.jpkc.entity.Section;
import com.study.jpkc.mapper.SectionMapper;
import com.study.jpkc.service.IResourceService;
import com.study.jpkc.service.ISectionService;
import com.study.jpkc.utils.FileUtils;
import com.study.jpkc.utils.GenerateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section> implements ISectionService {

    private final OssComponent ossComponent;

    private final IResourceService resourceService;

    private final SectionMapper sectionMapper;

    public SectionServiceImpl(OssComponent ossComponent, IResourceService resourceService, SectionMapper sectionMapper) {
        this.ossComponent = ossComponent;
        this.resourceService = resourceService;
        this.sectionMapper = sectionMapper;
    }

    @Override
    public boolean uploadSectionResource(String courseId, String sectionId, MultipartFile resourceFile) throws IOException {
        String resourceId = GenerateUtils.getUUID();
        if (ObjectUtil.isNotNull(resourceFile.getOriginalFilename())) {
            URL url = ossComponent.upload(OssConstant.COURSE_PATH + courseId + "/" + OssConstant.SECTION_PATH + resourceId + FileUtils.getFileSuffix(resourceFile.getOriginalFilename()), resourceFile.getBytes());
            Resource resource = new Resource();
            resource.setResourceId(resourceId);
            resource.setResourceType(1);
            resource.setSectionId(sectionId);
            resource.setResourcePath(FileUtils.getFileUrlPath(url));
            return resourceService.save(resource);
        }
        return false;
    }

    @Override
    public Map<Section, List<Resource>> getDetailByCourseId(String courseId) {
        List<Section> sectionList = sectionMapper.selectList(new QueryWrapper<Section>().eq("course_id", courseId).orderByAsc("section_no"));
        Map<Section, List<Resource>> sectionMap = new LinkedHashMap<>();
        for (Section section : sectionList) {
            sectionMap.put(section, resourceService.list(new QueryWrapper<Resource>().eq("section_id", section.getSectionId())));
        }
        return sectionMap;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        return sectionMapper.delete(new QueryWrapper<Section>().eq("course_id", courseId)) == 1;
    }
}
