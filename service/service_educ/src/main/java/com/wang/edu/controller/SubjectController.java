package com.wang.edu.controller;


import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.subject.OneSubject;
import com.wang.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-24
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {
@Autowired
private SubjectService subjectService;
@PostMapping("addSubject")
    public ResultJson addSubject(MultipartFile file){
    subjectService.saveSubject(file,subjectService);
    return ResultJson.ok();
}
@GetMapping("getSubject")
    public ResultJson getSubject(){
    List<OneSubject> oneSubjectList=subjectService.getSubjectList();
    return ResultJson.ok().data("items",oneSubjectList);
}
}

