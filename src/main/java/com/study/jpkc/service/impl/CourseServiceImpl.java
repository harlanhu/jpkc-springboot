package com.study.jpkc.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.common.component.OssComponent;
import com.study.jpkc.common.constant.OssConstant;
import com.study.jpkc.entity.Course;
import com.study.jpkc.entity.Teacher;
import com.study.jpkc.mapper.CourseMapper;
import com.study.jpkc.mapper.TeacherMapper;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.task.CourseScheduleTask;
import com.study.jpkc.utils.FileUtils;
import com.study.jpkc.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    private final CourseMapper courseMapper;

    private final TeacherMapper teacherMapper;

    private final OssComponent ossComponent;

    private final RedisUtils redisUtils;

    public CourseServiceImpl(CourseMapper courseMapper, RedisUtils redisUtils, TeacherMapper teacherMapper, OssComponent ossComponent) {
        this.courseMapper = courseMapper;
        this.redisUtils = redisUtils;
        this.teacherMapper = teacherMapper;
        this.ossComponent = ossComponent;
    }

    @Override
    public List<Course> selectAllCourse() {
        return courseMapper.selectAllCourse();
    }

    @Override
    public List<Course> findCourseByUserId(String userId) {
        return courseMapper.selectCourseByUserId(userId);
    }

    @Override
    public List<Course> getRanking(Integer current, Integer size) {
        int start = (current - 1) * size;
        int end = current * size - 1;
        if (end > redisUtils.getListLength(CourseScheduleTask.COURSE_TOP_50_KEY)) {
            end = -1;
        }
        return redisUtils.getList(CourseScheduleTask.COURSE_TOP_50_KEY, start, end)
                .stream().map(item -> (Course)item).collect(Collectors.toList());
    }

    @Override
    public List<Course> getNew(Integer current, Integer size) {
        int start = (current - 1) * size;
        int end = current * size - 1;
        if (end > redisUtils.getListLength(CourseScheduleTask.COURSE_NEW_KEY)) {
            end = -1;
        }
        return redisUtils.getList(CourseScheduleTask.COURSE_NEW_KEY, start, end)
                .stream().map(item -> (Course)item).collect(Collectors.toList());
    }

    @Override
    public List<Course> getStar(Integer current, Integer size) {
        int start = (current - 1) * size;
        int end = current * size - 1;
        if (end > redisUtils.getListLength(CourseScheduleTask.COURSE_STAR_KEY)) {
            end = -1;
        }
        return redisUtils.getList(CourseScheduleTask.COURSE_STAR_KEY, start, end)
                .stream().map(item -> (Course)item).collect(Collectors.toList());
    }

    @Override
    public Page<Course> getByLabelId(String labelId, Integer current, Integer size) {
        return courseMapper.selectByLabelId(new Page<>(current, size), labelId);
    }

    @Override
    public Page<Course> getByCategoryId(String categoryId, Integer current, Integer size) {
        return courseMapper.selectByCategoryId(new Page<>(current, size), categoryId);
    }

    @Override
    public String create(AccountProfile accountProfile, Course course) {
        String courseId = UUID.randomUUID().toString().replace("-", "");
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("user_id", accountProfile.getUserId()));
        course.setTeacherId(teacher.getTeacherId());
        course.setSchoolId(teacher.getSchoolId());
        course.setCourseId(courseId);
        if (courseMapper.insert(course) == 1) {
            ossComponent.upload(OssConstant.COURSE_PATH + courseId + "/readme.txt", JSON.toJSONString(course));
            return courseId;
        } else {
            return null;
        }
    }

    @Override
    public Boolean uploadLogo(String courseId, MultipartFile logoFile) throws IOException {
        if (logoFile.getOriginalFilename() != null) {
            URL url = ossComponent.upload(OssConstant.COURSE_PATH + courseId + "/logo/courseLogo" + FileUtils.getFileSuffix(logoFile.getOriginalFilename()), logoFile.getBytes());
            Course course = new Course();
            course.setCourseId(courseId);
            course.setCourseLogo(url.toString());
            return courseMapper.updateById(course) == 1;
        }
        return false;
    }
}
