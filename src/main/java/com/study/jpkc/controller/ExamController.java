package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.common.dto.ExamDto;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.Exam;
import com.study.jpkc.entity.Score;
import com.study.jpkc.service.IExamService;
import com.study.jpkc.service.IScoreService;
import com.study.jpkc.shiro.AccountProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2021-04-30
 */
@RestController
@Slf4j
@RequestMapping("/exam")
public class ExamController {

    private final IExamService examService;

    private final IScoreService scoreService;

    public ExamController(IExamService examService, IScoreService scoreService) {
        this.examService = examService;
        this.scoreService = scoreService;
    }

    @PostMapping("/add/{courseId}")
    public Result addExam(@PathVariable("courseId") String courseId, @RequestBody List<ExamDto> examDtoList) {
        List<Exam> examList = new ArrayList<>();
        Exam exam = null;
        StringBuilder options = null;
        ExamDto examDto = null;
        for (int i = 0; i < examDtoList.size(); i++) {
            options = new StringBuilder();
            examDto = examDtoList.get(i);
            for (int j = 0; j < examDto.getOptions().size(); j++) {
                if ((j + 1) == examDto.getOptions().size()) {
                    options.append(examDto.getOptions().get(j));
                } else {
                    options.append(examDto.getOptions().get(j)).append("/");
                }
            }
            exam = new Exam(
                    courseId,
                    examDto.getTopic(),
                    options.toString(),
                    examDto.getAnswer(),
                    examDto.getValue(),
                    i
            );
            examList.add(exam);
        }
        if (examService.saveBatch(examList)) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/get/{courseId}")
    public Result getByCourseId(@PathVariable("courseId") String courseId) {
        List<Exam> examList = examService.list(new QueryWrapper<Exam>().eq("courseId", courseId))
                .stream().sorted(Comparator.comparing(Exam::getNo))
                .collect(Collectors.toList());
        List<ExamDto> examDtoList = new ArrayList<>();
        for (Exam exam : examList) {
            List<String> options = Arrays.asList(exam.getOptions().split("/"));
            ExamDto examDto = BeanUtil.copyProperties(exam, ExamDto.class);
            examDto.setOptions(options);
            examDtoList.add(examDto);
        }
        return Result.getSuccessRes(examDtoList);
    }

    @PostMapping("/rating/{courseId}")
    public Result rating(@PathVariable("courseId") String courseId, @RequestBody int[] answers) {
        List<Exam> examList = examService.list(new QueryWrapper<Exam>().eq("courseId", courseId))
                .stream().sorted(Comparator.comparing(Exam::getNo))
                .collect(Collectors.toList());
        double mark = 0;
        for (int i = 0; i < examList.size(); i++) {
            if (examList.get(i).getAnswer().equals(answers[i])) {
                mark += examList.get(i).getValue();
            }
        }
        scoreService.remove(new QueryWrapper<Score>().eq("course_id", courseId).eq("user_id", ((AccountProfile) SecurityUtils.getSubject().getPrincipal()).getUserId()));
        Score score = new Score();
        score.setScoreId(IdUtil.simpleUUID());
        score.setCourseId(courseId);
        score.setUserId(((AccountProfile) SecurityUtils.getSubject().getPrincipal()).getUserId());
        score.setMark(mark);
        score.setCreateTime(LocalDate.now());
        score.setSchedule(100.0);
        scoreService.save(score);
        return Result.getSuccessRes(mark);
    }
}
