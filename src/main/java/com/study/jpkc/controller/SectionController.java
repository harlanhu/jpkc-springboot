package com.study.jpkc.controller;


import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Section;
import com.study.jpkc.service.ISectionService;
import com.study.jpkc.utils.FileUtils;
import com.study.jpkc.utils.GenerateUtils;
import lombok.SneakyThrows;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/section")
public class SectionController {

    private final ISectionService sectionService;

    public SectionController(ISectionService sectionService) {
        this.sectionService = sectionService;
    }

    @RequiresUser
    @PostMapping("/save")
    public Result saveSection(@RequestBody Section section) {
        String sectionId = GenerateUtils.getUUID();
        section.setSectionId(sectionId);
        if (sectionService.save(section)) {
            return Result.getSuccessRes(sectionId);
        } else {
            return Result.getFailRes("创建失败，请稍后重试！");
        }
    }

    @SneakyThrows
    @RequiresUser
    @PostMapping("/uploadSectionResource/{courseId}/{sectionId}")
    public Result uploadSectionResource(@PathVariable String courseId, @PathVariable String sectionId, @RequestParam MultipartFile resourceFile) {
        if (resourceFile.isEmpty()) {
            return Result.getFailRes("文件上传失败");
        }
        if (!FileUtils.isTypeOfVideo(resourceFile)) {
            return Result.getFailRes("文件格式不正确！");
        }
        if (sectionService.uploadSectionResource(courseId, sectionId, resourceFile)) {
            return Result.getSuccessRes("上传成功！");
        }
        return Result.getFailRes("上传失败！");
    }
}
