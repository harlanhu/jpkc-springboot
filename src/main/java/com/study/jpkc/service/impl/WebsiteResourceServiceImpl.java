package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.WebsiteResource;
import com.study.jpkc.mapper.WebsiteResourceMapper;
import com.study.jpkc.service.IWebsiteResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public IPage<WebsiteResource> findWebResourceByLayoutName(Integer current, Integer size, String layoutName) {
        return websiteResourceMapper.selectWebResourceByLayoutName(new Page<>(current, size), layoutName);
    }
}
