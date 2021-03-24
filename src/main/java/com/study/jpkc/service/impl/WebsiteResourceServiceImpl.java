package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.WebsiteResource;
import com.study.jpkc.mapper.WebsiteResourceMapper;
import com.study.jpkc.service.IWebsiteResourceService;
import com.study.jpkc.utils.GenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
@Service
public class WebsiteResourceServiceImpl extends ServiceImpl<WebsiteResourceMapper, WebsiteResource> implements IWebsiteResourceService {

    @Autowired
    private WebsiteResourceMapper websiteResourceMapper;

    @Override
    public IPage<WebsiteResource> findWebResourcesByLayoutName(Integer current, Integer size, String layoutName) {
        return websiteResourceMapper.selectWebResourceByLayoutName(new Page<>(current, size), layoutName);
    }

    @Override
    public List<WebsiteResource> findWebResourcesByLayoutName(String layoutName) {
        return websiteResourceMapper.selectWebResourcesByLayoutName(layoutName);
    }

    @Override
    public boolean saveCourse(String courseId) {
        WebsiteResource wResource = new WebsiteResource();
        wResource.setResourceId(GenerateUtils.getUUID());
        wResource.setResourceDesc("课程资源");
        wResource.setResourceName("课程资源");
        wResource.setResourceWeight(0);
        wResource.setAssociateDataId(courseId);
        return websiteResourceMapper.insert(wResource) == 1;
    }

}
