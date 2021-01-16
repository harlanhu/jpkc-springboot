package com.study.jpkc.mapper;

import com.study.jpkc.entity.WebsiteResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
public interface WebsiteResourceMapper extends BaseMapper<WebsiteResource> {

    /**
     * 通过布局名称查询布局资源
     * @param layoutName 布局名称
     * @return 布局资源
     */
    List<WebsiteResource> selectWebResourceByLayoutName(String layoutName);
}
