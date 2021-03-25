package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.School;
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

    /**
     * 通过布局查询学校
     * @param layoutId 布局id
     * @return 学校信息
     */
    List<School> selectByLayout(String layoutId);

    /**
     * 获取未绑定布局的学校
     * @param layoutId 布局id
     * @param page 分页信息
     * @return 学校信息
     */
    Page<School> selectWithoutLayout(@Param("layoutId") String layoutId, IPage<School> page);
}
