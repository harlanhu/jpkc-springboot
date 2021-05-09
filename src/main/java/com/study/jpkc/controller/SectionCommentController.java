package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.dto.SCommentDto;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.SectionComment;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.ISectionCommentService;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.GenerateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    private final IUserService userService;

    public SectionCommentController(ISectionCommentService sCommentService, IUserService userService) {
        this.sCommentService = sCommentService;
        this.userService = userService;
    }

    @RequiresUser
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

    @RequiresUser
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
        List<SectionComment> sCommentList = page.getRecords();
        List<SCommentDto> sCommentDtoList = new ArrayList<>();
        User user;
        for (SectionComment sComment : sCommentList) {
            user = userService.getById(sComment.getUserId());
            SCommentDto sCommentDto = BeanUtil.toBean(sComment, SCommentDto.class);
            sCommentDto.setUserAvatar(user.getUserAvatar());
            sCommentDto.setUserName(user.getUsername());
            sCommentDtoList.add(sCommentDto);
        }
        return Result.getSuccessRes(MapUtil.builder()
                .put("total", page.getTotal())
                .put("pages", page.getPages())
                .put("list", sCommentDtoList).build());
    }

    @GetMapping("/getByUser/{current}/{size}")
    public Result getByUser(@PathVariable Integer current, @PathVariable Integer size) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        Page<SectionComment> page = sCommentService.page(new Page<>(current, size), new QueryWrapper<SectionComment>().eq("user_id", account.getUserId()));
        return Result.getSuccessRes(PageVo.getPageVo(page));
    }

    @RequiresUser
    @GetMapping("/like/{sectionCommentId}")
    public Result like(@PathVariable String sectionCommentId) {
        SectionComment sComment = sCommentService.getById(sectionCommentId);
        sComment.setCommentStar(sComment.getCommentStar() + 1);
        boolean isSuccess = sCommentService.updateById(sComment);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

}
