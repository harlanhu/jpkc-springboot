package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.jpkc.entity.School;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Mapper
public interface SchoolMapper extends BaseMapper<School> {

    /**
     * 通过教师id查询学校信息
     * @param teacherId 教师id
     * @return 学校信息
     */
    School selectByTeacherId(String teacherId);

    /**
     * 通过课程id查询学校信息
     * @param courseId 课程id
     * @return 学校信息
     */
    School selectByCourseId(String courseId);

    /**
     * 通过资源id查询学校名称
     * @param resourceId 资源id
     * @return 学校名称
     */
    String selectNameByResourceId(String resourceId);
}
