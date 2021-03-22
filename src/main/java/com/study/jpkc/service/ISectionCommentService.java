package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.SectionComment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-03-22
 */
public interface ISectionCommentService extends IService<SectionComment> {

    /**
     * 分页获取章节评论信息
     * @param sectionId 章节id
     * @param current 当前页
     * @param size 每页大小
     * @param rankType 排序方式
     * @return 评论信息
     */
    Page<SectionComment> getBySectionId(String sectionId, Integer current, Integer size, Integer rankType);

}
