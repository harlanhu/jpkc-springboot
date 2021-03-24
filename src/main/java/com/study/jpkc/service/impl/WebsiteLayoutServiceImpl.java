package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.WebsiteLayout;
import com.study.jpkc.mapper.WebsiteLayoutMapper;
import com.study.jpkc.service.IWebsiteLayoutService;
import com.study.jpkc.utils.GenerateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
@Service
public class WebsiteLayoutServiceImpl extends ServiceImpl<WebsiteLayoutMapper, WebsiteLayout> implements IWebsiteLayoutService {

    private final WebsiteLayoutMapper layoutMapper;

    public WebsiteLayoutServiceImpl(WebsiteLayoutMapper layoutMapper) {
        this.layoutMapper = layoutMapper;
    }

    @Override
    public boolean bindResource(String userId, String layoutId, String resourceId, String desc) {
        return layoutMapper.insertBindResource(GenerateUtils.getUUID(), layoutId, resourceId, LocalDateTime.now(), userId, desc) == 1;
    }

    @Override
    public boolean bindCourse(String layoutId, String courseId) {
        return layoutMapper.insertBindCourse(GenerateUtils.getUUID(), layoutId, courseId) == 1;
    }

    @Override
    public boolean unbindCourse(String courseId) {
        return layoutMapper.deleteBindCourse(courseId) == 1;
    }
}
