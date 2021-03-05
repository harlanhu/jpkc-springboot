package com.study.jpkc.service;

import com.study.jpkc.entity.Section;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
     * @exception IOException 异常
     * @return 是否成功
     */
    boolean uploadSectionResource(String courseId, String sectionId, MultipartFile resourceFile) throws IOException;
}
