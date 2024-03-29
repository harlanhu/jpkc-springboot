package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 查询所有课程
     * @return 所有课程
     */
    List<Course> selectAllCourse();


    /**
     * 通过userId查询用户订阅课程
     * @param userId 用户id
     * @return 订阅课程
     */
    List<Course> selectCourseByUserId(String userId);


    /**
     * 通过labelId查询课程
     * @param page 分页信息
     * @param labelId 标签id
     * @return 课程信息
     */
    Page<Course> selectByLabelId(IPage<Course> page, @Param("labelId") String labelId);


    /**
     * 通过类别id查询课程
     * @param page 分页信息
     * @param categoryId 类别id
     * @return 课程信息
     */
    Page<Course> selectByCategoryId(IPage<Course> page, @Param("categoryId") String categoryId);

    /**
     * 获取类别排行
     * @param page 分页信息
     * @param categoryId 类别id
     * @return 课程信息
     */
    Page<Course> selectRankingByCategoryId(IPage<Course> page, @Param("categoryId") String categoryId);

    /**
     * 用户与课程关系绑定
     * @param innerId 标识
     * @param userId 用户id
     * @param courseId 课程id
     * @return 影响行数
     */
    int bindUserWithCourse(@Param("innerId") String innerId, @Param("userId") String userId, @Param("courseId") String courseId);

    /**
     * 通过userId获取收藏
     * @param userId 用户id
     * @param page 分页信息
     * @return 收藏课程
     */
    Page<Course> selectCollectByUserId(@Param("userId") String userId, IPage<Course> page);

    /**
     * 通过类别查询课程id
     * @param categoryId 类别id
     * @return 课程id
     */
    List<String> selectIdByCategoryId(String categoryId);

    /**
     * 获取推荐课程
     * @param page 分页信息
     * @return 课程信息
     */
    Page<Course> selectRecommend(IPage<Course> page);

    /**
     * 通过布局id搜索
     * @param layoutId 布局id
     * @param page 分页信息
     * @return 课程信息
     */
    Page<Course> selectByLayout(@Param("layoutId") String layoutId, IPage<Course> page);

    /**
     * 获取未绑定布局课程
     * @param layoutId 布局id
     * @param page 分页信息
     * @return 课程信息
     */
    Page<Course> selectWithoutLayout(@Param("layoutId") String layoutId, IPage<Course> page);

    /**
     * 取消收藏
     * @param userId 用户id
     * @param courseId 课程id
     * @return 邮箱行数
     */
    int unCollect(@Param("userId") String userId, @Param("courseId") String courseId);
}
