package com.study.jpkc.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.common.component.OssComponent;
import com.study.jpkc.common.constant.CourseConstant;
import com.study.jpkc.common.constant.OssConstant;
import com.study.jpkc.entity.*;
import com.study.jpkc.mapper.CourseMapper;
import com.study.jpkc.mapper.TeacherMapper;
import com.study.jpkc.service.*;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.task.CourseScheduleTask;
import com.study.jpkc.utils.FileUtils;
import com.study.jpkc.utils.GenerateUtils;
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

    private final ILabelService labelService;

    private final ICategoryService categoryService;

    private final IUserService userService;

    private final ISectionService sectionService;

    private final IResourceService resourceService;

    private final RedisUtils redisUtils;

    private CourseScheduleTask courseScheduleTask;

    public CourseServiceImpl(CourseMapper courseMapper, RedisUtils redisUtils, TeacherMapper teacherMapper,
                             OssComponent ossComponent, ILabelService labelService, ICategoryService categoryService,
                             IUserService userService, ISectionService sectionService, IResourceService resourceService) {
        this.courseMapper = courseMapper;
        this.redisUtils = redisUtils;
        this.teacherMapper = teacherMapper;
        this.ossComponent = ossComponent;
        this.labelService = labelService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.sectionService = sectionService;
        this.resourceService = resourceService;
    }

    public void setCourseScheduleTask(CourseScheduleTask courseScheduleTask) {
        this.courseScheduleTask = courseScheduleTask;
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
    public String save(AccountProfile accountProfile, Course course, MultipartFile logoFile, String categoryId, String[] labelNames) throws IOException {
        String courseId = UUID.randomUUID().toString().replace("-", "");
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("user_id", accountProfile.getUserId()));
        course.setTeacherId(teacher.getTeacherId());
        course.setSchoolId(teacher.getSchoolId());
        course.setCourseId(courseId);
        course.setCourseLogo("https://web-applications.oss-cn-chengdu.aliyuncs.com/jpck/course/default/logo/course-default-logo.png");
        if (courseMapper.insert(course) == 1) {
            ossComponent.upload(OssConstant.COURSE_PATH + courseId + "/readme.txt", JSON.toJSONString(course));
            if (ObjectUtil.isNotNull(logoFile)) {
                uploadLogo(courseId, logoFile);
            }
            List<Label> labels = labelService.saveLabels(labelNames);
            labelService.bindLabelsToCourse(courseId, labels);
            categoryService.bindCourse(courseId, categoryId);
            return courseId;
        }
        return null;
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

    @Override
    public boolean isBelong(String userId, String courseId) {
        return userId.equals(userService.getByCourseId(courseId).getUserId());
    }

    @Override
    public Page<Course> getRankingByCategoryId(String categoryId, Integer current, Integer size) {
        return courseMapper.selectRankingByCategoryId(new Page<>(current, size), categoryId);
    }

    @Override
    public boolean delete(String courseId) {
        int courseRow = courseMapper.delete(new QueryWrapper<Course>().eq(CourseConstant.COL_ID, courseId));
        categoryService.unbindCourse(courseId);
        List<Section> sectionList = sectionService.list();
        for (Section section : sectionList) {
            List<Section> childSection = sectionService.list(new QueryWrapper<Section>().eq("parent_id", section.getSectionId()));
            if (childSection.isEmpty()) {
                resourceService.remove(new QueryWrapper<Resource>().eq("section_id", section.getSectionId()));
            } else {
                //TODO: 有子章节进行删除，使用递归
                log.debug("递归删除子章节");
            }
            sectionService.removeByCourseId(courseId);
        }
        deleteWithRedis(courseId);
        return courseRow == 1;
    }

    @Override
    public boolean collect(String userId, String courseId) {
        Course course = courseMapper.selectById(courseId);
        course.setCourseStar(course.getCourseStar() + 1);
        courseMapper.updateById(course);
        return courseMapper.bindUserWithCourse(GenerateUtils.getUUID(), userId, courseId) == 1;
    }

    @Override
    public Page<Course> getCollectByUserId(String userId, Integer current, Integer size) {
        return courseMapper.selectCollectByUserId(userId, new Page<>(current, size));
    }

    @Override
    public IPage<Course> getOpenByType(Integer current, Integer size, Integer type) {
        Page<Course> pageInfo = new Page<>(current, size);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>().eq(CourseConstant.COL_STATUS, 0);
        return getByType(pageInfo, queryWrapper, type);
    }

    @Override
    public IPage<Course> getOpenByTypeAndCategory(Integer current, Integer size, Integer type, String categoryId) {
        Page<Course> pageInfo = new Page<>(current, size);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>().eq(CourseConstant.COL_STATUS, 0);
        if (!String.valueOf(0).equals(categoryId)) {
            queryWrapper.in(CourseConstant.COL_ID, courseMapper.selectIdByCategoryId(categoryId));
        }
        return getByType(pageInfo, queryWrapper, type);
    }

    @Override
    public IPage<Course> getOpenByTypeAndSchool(Integer current, Integer size, Integer type, String schoolId) {
        Page<Course> pageInfo = new Page<>(current, size);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>().eq(CourseConstant.COL_STATUS, 0).eq(CourseConstant.COL_SCHOOL_ID, schoolId);
        return getByType(pageInfo, queryWrapper, type);
    }

    /**
     * 在缓存中删除课程数据
     * @param courseId 课程id
     */
    private void deleteWithRedis(String courseId) {
        getRanking(0, 50).forEach(course -> {
            if (courseId.equals(course.getCourseId())) {
                courseScheduleTask.courseStarTask();
            }
        });
        getNew(0, 50).forEach(course -> {
            if (courseId.equals(course.getCourseId())) {
                courseScheduleTask.courseNewTask();
            }
        });
        getRanking(0, 50).forEach(course -> {
            if (courseId.equals(course.getCourseId())) {
                courseScheduleTask.courseWeekTopTask();
            }
        });
    }

    /**
     * 获取课程信息
     * @param pageInfo 分页信息
     * @param queryWrapper 查询信息
     * @param type 类别
     * @return 课程信息
     */
    private IPage<Course> getByType(IPage<Course> pageInfo, QueryWrapper<Course> queryWrapper, Integer type) {
        if (CourseConstant.COURSE_ALL == type) {
            return courseMapper.selectPage(pageInfo, queryWrapper);
        } else if (CourseConstant.COURSE_POPULAR == type) {
            return courseMapper.selectPage(pageInfo, queryWrapper.orderBy(true, false, CourseConstant.COL_VIEWS));
        } else if (CourseConstant.COURSE_COLLECT == type) {
            return courseMapper.selectPage(pageInfo, queryWrapper.orderBy(true, false, CourseConstant.COL_STAR));
        } else if (CourseConstant.COURSE_FREE == type) {
            return courseMapper.selectPage(pageInfo, queryWrapper.eq(CourseConstant.COL_PRICE, 0));
        } else if (CourseConstant.COURSE_CHARGE == type) {
            return courseMapper.selectPage(pageInfo, queryWrapper.ne(CourseConstant.COL_PRICE, 0));
        } else {
            return null;
        }
    }
}
