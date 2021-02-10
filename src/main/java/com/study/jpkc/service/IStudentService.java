package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface IStudentService extends IService<Student> {

    /**
     * 获取全部学生信息（分页）
     * @param current 当前页
     * @param size 每页大小
     * @return page信息
     */
    Page<Student> getAll(Integer current, Integer size);
}
