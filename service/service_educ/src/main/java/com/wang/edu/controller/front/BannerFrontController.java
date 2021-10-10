package com.wang.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.Course;
import com.wang.edu.bean.Teacher;
import com.wang.edu.service.CourseService;
import com.wang.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/bannerFront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @GetMapping("indexInfo")
    public ResultJson index(){
        QueryWrapper<Teacher> teacherQueryWrapper=new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("sort");
        teacherQueryWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherQueryWrapper);
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit 8");
        List<Course> courseList = courseService.list(courseQueryWrapper);
        return ResultJson.ok().data("teacherList",teacherList).data("courseList",courseList);
    }
}
