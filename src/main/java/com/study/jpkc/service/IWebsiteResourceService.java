package com.study.jpkc.service;

import com.study.jpkc.entity.WebsiteResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
public interface IWebsiteResourceService extends IService<WebsiteResource> {

    /**
     * 通过布局名称查询布局资源
     * @param layoutName 布局名称
     * @return 布局资源
     */
    List<WebsiteResource> findWebResourceByLayoutName(String layoutName);
}
