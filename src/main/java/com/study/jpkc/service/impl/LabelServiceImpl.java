package com.study.jpkc.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.Label;
import com.study.jpkc.mapper.LabelMapper;
import com.study.jpkc.service.ILabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

    private final LabelMapper labelMapper;

    public LabelServiceImpl(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }

    @Override
    public List<Label> getByCourseId(String courseId) {
        return labelMapper.selectByCourseId(courseId);
    }

    @Override
    public List<Label> saveLabels(String... labelName) {
        Label label = null;
        List<Label> labelList = new ArrayList<>();
        for (String name : labelName) {
            label = labelMapper.selectOne(new QueryWrapper<Label>().eq("label_name", name));
            if (ObjectUtil.isNotNull(label)) {
                labelList.add(label);
            } else {
                label = new Label();
                label.setLabelName(name);
                label.setLabelId(UUID.randomUUID().toString().replace("-", ""));
                label.setLabelDesc("用户默认创建标签");
                if (labelMapper.insert(label) == 1) {
                    labelList.add(label);
                }
            }
        }
        return labelList;
    }

    @Override
    public void bindLabelsToCourse(String courseId, List<Label> labels) {
        for (Label label : labels) {
            labelMapper.bindLabelToCourse(UUID.randomUUID().toString().replace("-", ""), courseId, label);
        }
    }

    @Override
    public boolean unbindCourses(String courseId) {
        return labelMapper.unbindCourses(courseId) != 0;
    }
}
