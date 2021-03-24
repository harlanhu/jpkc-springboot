package com.study.jpkc.mapper;

import com.study.jpkc.entity.WebsiteLayout;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-01-01
 */
public interface WebsiteLayoutMapper extends BaseMapper<WebsiteLayout> {

    /**
     * 插入绑定资源数据
     * @param inner 中间表id
     * @param layoutId 布局id
     * @param resourceId 资源id
     * @param create 绑定时间
     * @param userId 操作人
     * @param desc 描述
     * @return 影响行数
     */
    int insertBindResource(@Param("inner") String inner,@Param("layoutId") String layoutId,@Param("resourceId") String resourceId,
                           @Param("create") LocalDateTime create,@Param("userId") String userId,@Param("desc") String desc);

    /**
     * 插入绑定课程数据
     * @param inner 中间表id
     * @param layoutId 布局id
     * @param courseId 资源id
     * @return 影响行数
     */
    int insertBindCourse(@Param("inner") String inner,@Param("layoutId") String layoutId,@Param("courseId") String courseId);

    /**
     * 解绑布局课程
     * @param courseId 课程id
     * @return 影响行数
     */
    int deleteBindCourse(@Param("courseId") String courseId);

    /**
     * 插入绑定学校数据
     * @param inner 中间表id
     * @param layoutId 布局id
     * @param schoolId 资源id
     * @return 影响行数
     */
    int insertBindSchool(@Param("inner") String inner, @Param("layoutId") String layoutId, @Param("schoolId") String schoolId);
}
