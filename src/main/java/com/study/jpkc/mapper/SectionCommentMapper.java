package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.SectionComment;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-03-22
 */
public interface SectionCommentMapper extends BaseMapper<SectionComment> {

    /**
     * 获取根评论按时间顺序排序
     * @param sectionId 章节id
     * @param page 分页信息
     * @return 评论信息
     */
    Page<SectionComment> selectBySectionIdWithTime(@Param("sectionId") String sectionId, IPage<SectionComment> page);

    /**
     * 获取根评论按时间倒叙
     * @param sectionId 章节id
     * @param page 分页信息
     * @return 评论信息
     */
    Page<SectionComment> selectBySectionIdWithNew(@Param("sectionId") String sectionId, IPage<SectionComment> page);

    /**
     * 获取根评论按点赞排序
     * @param sectionId 章节id
     * @param page 分页信息
     * @return 评论信息
     */
    Page<SectionComment> selectBySectionIdWithStar(@Param("sectionId") String sectionId, IPage<SectionComment> page);
}
