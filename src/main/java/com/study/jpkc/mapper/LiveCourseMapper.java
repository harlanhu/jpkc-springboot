package com.study.jpkc.mapper;

import com.study.jpkc.entity.LiveCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-02-05
 */
public interface LiveCourseMapper extends BaseMapper<LiveCourse> {

    /**
     * 通过userid查询直播课程
     * @param userId userid
     * @return 直播课程
     */
    List<LiveCourse> selectByUserId(String userId);

}
