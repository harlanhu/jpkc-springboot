package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.Resource;
import com.study.jpkc.entity.Section;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface ISectionService extends IService<Section> {

    /**
     * 章节上传资源文件
     * @param courseId 课程id
     * @param sectionId 章节id
     * @param resourceFile 资源文件
     * @throws IOException 异常
     * @return 是否成功
     */
    boolean uploadSectionResource(String courseId, String sectionId, MultipartFile resourceFile) throws IOException;

    /**
     * 通过课程id获取章节详情
     * @param courseId 课程id
     * @return 章节详情
     */
    Map<Section, List<Resource>> getDetailByCourseId(String courseId);

    /**
     * 通过课程id删除章节
     * @param courseId 课程id
     * @return 是否成功
     */
    boolean removeByCourseId(String courseId);
}
