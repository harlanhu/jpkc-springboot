package com.study.jpkc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.common.component.OssComponent;
import com.study.jpkc.common.constant.OssConstant;
import com.study.jpkc.entity.LiveCourse;
import com.study.jpkc.mapper.LiveCourseMapper;
import com.study.jpkc.service.ILiveCourseService;
import com.study.jpkc.utils.FileUtils;
import com.study.jpkc.utils.GenerateUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-02-05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements ILiveCourseService {

    private final LiveCourseMapper liveCourseMapper;

    private final OssComponent ossComponent;

    public LiveCourseServiceImpl(LiveCourseMapper liveCourseMapper, OssComponent ossComponent) {
        this.liveCourseMapper = liveCourseMapper;
        this.ossComponent = ossComponent;
    }

    @Override
    public Page<LiveCourse> getLiveCourse(Integer current, Integer size) {
        return liveCourseMapper.selectPage(new Page<>(current, size), new QueryWrapper<LiveCourse>().orderBy(true,true,"reserve_time"));
    }

    @Override
    public List<LiveCourse> getByUserId(String userId) {
        return liveCourseMapper.selectByUserId(userId);
    }

    @SneakyThrows
    @Override
    public boolean save(String teacherId, LiveCourse lCourse, MultipartFile logoFile) {
        String lCourseId = GenerateUtils.getUUID();
        URL url = ossComponent.upload(
                OssConstant.LIVE_COURSE_PATH +
                        lCourseId + "/logo/logo." + FileUtils.getFileSuffix(Objects.requireNonNull(logoFile.getOriginalFilename())), logoFile.getBytes());
        lCourse.setUrl("http://47.108.151.199:8080/hls/" + lCourseId + ".m3u8");
        lCourse.setTeacherId(teacherId);
        lCourse.setCreated(LocalDateTime.now());
        lCourse.setStar(0);
        lCourse.setAvatar(FileUtils.getFileUrlPath(url));
        lCourse.setLiveCourseId(lCourseId);
        return liveCourseMapper.insert(lCourse) == 1;
    }

    @Override
    public boolean addViews(String lCourseId) {
        LiveCourse lCourse = liveCourseMapper.selectById(lCourseId);
        if (ObjectUtil.isNotNull(lCourse)) {
            lCourse.setViews(lCourse.getViews() + 1);
            return liveCourseMapper.updateById(lCourse) == 1;
        }
        return false;
    }

    @Override
    public boolean addStar(String lCourseId) {
        LiveCourse lCourse = liveCourseMapper.selectById(lCourseId);
        if (ObjectUtil.isNotNull(lCourse)) {
            lCourse.setStar(lCourse.getStar() + 1);
            return liveCourseMapper.updateById(lCourse) == 1;
        }
        return false;
    }
}
