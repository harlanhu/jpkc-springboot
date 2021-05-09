package com.study.jpkc.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.common.dto.ScoreExelDto;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Course;
import com.study.jpkc.entity.Score;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.ICourseService;
import com.study.jpkc.service.IScoreService;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    private final IScoreService scoreService;

    private final IUserService userService;

    private final ICourseService courseService;

    public ScoreController(IScoreService scoreService, IUserService userService, ICourseService courseService) {
        this.scoreService = scoreService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/exist/{courseId}")
    public Result exist(@PathVariable("courseId") String courseId) {
        Score score = scoreService.getOne(new QueryWrapper<Score>().eq("course_id", courseId).eq("user_id", ((AccountProfile) SecurityUtils.getSubject().getPrincipal()).getUserId()));
        return Result.getSuccessRes(ObjectUtil.isNotNull(score));
    }

    @RequiresUser
    @GetMapping("/getByCourseId/{courseId}")
    public Result getByCourseId(@PathVariable("courseId") String courseId) {
        return Result.getSuccessRes(scoreService.getOne(new QueryWrapper<Score>().eq("course_id", courseId).eq("user_id", ((AccountProfile) SecurityUtils.getSubject().getPrincipal()).getUserId())));
    }

    @RequiresUser
    @GetMapping("/getExelByCourseId/{courseId}")
    public void getExelByCourseId(@PathVariable("courseId") String courseId, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        resp.setHeader("Content-Disposition","attachment;filename=score.xls");
        List<Score> scoreList = scoreService.list(new QueryWrapper<Score>().eq("course_id", courseId));
        List<ScoreExelDto> scoreExelDtoList = new ArrayList<>();
        Course course = courseService.getById(courseId);
        for (Score score : scoreList) {
            User user = userService.getById(score.getUserId());
            scoreExelDtoList.add(new ScoreExelDto(
                    user.getUserId(),
                    user.getUsername(),
                    user.getUserPhone(),
                    user.getUserEmail(),
                    score.getMark(),
                    score.getCreateTime()
            ));
        }
        List<ScoreExelDto> exelDto = scoreExelDtoList.stream().sorted(Comparator.comparing(ScoreExelDto::getMark)).collect(Collectors.toList());
        ExcelWriter excelWriter = ExcelUtil.getWriter();
        excelWriter.getStyleSet().setAlign(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        for (int i = 0; i < 6; i++) {
            excelWriter.setColumnWidth(i, 30);
        }
        excelWriter.merge(5, course.getCourseName() + " - 课程成绩表");
        excelWriter.addHeaderAlias("userId", "用户ID");
        excelWriter.addHeaderAlias("username", "用户名");
        excelWriter.addHeaderAlias("userPhone", "联系电话");
        excelWriter.addHeaderAlias("userEmail", "联系邮箱");
        excelWriter.addHeaderAlias("mark", "当前成绩");
        excelWriter.addHeaderAlias("submitTime", "提交时间");
        ServletOutputStream out = resp.getOutputStream();
        excelWriter.write(exelDto, true);
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(out);
    }

}
