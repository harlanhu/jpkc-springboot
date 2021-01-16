package com.study.jpkc.service.impl;

import com.study.jpkc.entity.WebsiteResource;
import com.study.jpkc.mapper.WebsiteResourceMapper;
import com.study.jpkc.service.IWebsiteResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public List<WebsiteResource> findWebResourceByLayoutName(String layoutName) {
        return websiteResourceMapper.selectWebResourceByLayoutName(layoutName);
    }
}
