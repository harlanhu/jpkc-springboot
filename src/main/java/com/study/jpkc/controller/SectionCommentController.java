package com.study.jpkc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.SectionComment;
import com.study.jpkc.service.ISectionCommentService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.GenerateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/section-comment")
public class SectionCommentController {

    private final ISectionCommentService sCommentService;

    public SectionCommentController(ISectionCommentService sCommentService) {
        this.sCommentService = sCommentService;
    }

    @PostMapping("/save")
    public Result save(@RequestBody SectionComment sComment) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        sComment.setUserId(account.getUserId());
        sComment.setCommentId(GenerateUtils.getUUID());
        boolean isSuccess = sCommentService.save(sComment);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/remove/{commentId}")
    public Result delete(@PathVariable String commentId) {
        boolean isSuccess = sCommentService.removeById(commentId);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/getBySectionId/{sectionId}/{current}/{size}/{rankType}")
    public Result getBySectionId(@PathVariable String sectionId, @PathVariable Integer current, @PathVariable Integer size, @PathVariable Integer rankType) {
        Page<SectionComment> page = sCommentService.getBySectionId(sectionId, current, size, rankType);
        return Result.getSuccessRes(PageVo.getPageVo(page));
    }

    @GetMapping("/getByUser/{current}/{size}")
    public Result getByUser(@PathVariable Integer current, @PathVariable Integer size) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        Page<SectionComment> page = sCommentService.page(new Page<>(current, size), new QueryWrapper<SectionComment>().eq("user_id", account.getUserId()));
        return Result.getSuccessRes(PageVo.getPageVo(page));
    }

}
