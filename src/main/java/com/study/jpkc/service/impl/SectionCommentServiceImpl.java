package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.SectionComment;
import com.study.jpkc.mapper.SectionCommentMapper;
import com.study.jpkc.service.ISectionCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-03-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SectionCommentServiceImpl extends ServiceImpl<SectionCommentMapper, SectionComment> implements ISectionCommentService {

    private final SectionCommentMapper sCommentMapper;

    private static final int TIME_RANK_TYPE = 0;

    private static final int STAR_RANK_TYPE = 1;

    private static final int NEW_RANK_TYPE = 2;

    public SectionCommentServiceImpl(SectionCommentMapper sCommentMapper) {
        this.sCommentMapper = sCommentMapper;
    }

    @Override
    public Page<SectionComment> getBySectionId(String sectionId, Integer current, Integer size, Integer rankType) {
        Page<SectionComment> page = new Page<>(current, size);
        if (rankType == TIME_RANK_TYPE) {
            return sCommentMapper.selectBySectionIdWithTime(sectionId, page);
        } else if (rankType == STAR_RANK_TYPE) {
            return sCommentMapper.selectBySectionIdWithStar(sectionId, page);
        } else if (rankType == NEW_RANK_TYPE) {
            return sCommentMapper.selectBySectionIdWithNew(sectionId, page);
        }
        return null;
    }
}
