package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.WebsiteResource;

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
     * @param page 分页信息
     * @return 布局资源
     */
    IPage<WebsiteResource> selectWebResourceByLayoutName(Page<?> page, String layoutName);
}
