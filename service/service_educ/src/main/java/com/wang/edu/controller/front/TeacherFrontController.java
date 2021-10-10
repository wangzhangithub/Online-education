package com.wang.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.Course;
import com.wang.edu.bean.Teacher;
import com.wang.edu.service.CourseService;
import com.wang.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/edu/teacherfront")
public class TeacherFrontController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @GetMapping("teacherPage/{page}/{limit}")
    public ResultJson getTeacherPage(@PathVariable long page,
                                     @PathVariable long limit){
        Page<Teacher> teacherPage=new Page<>(page,limit);
        Map<Object,Object> map=teacherService.PageListWeb(teacherPage);
        return ResultJson.ok().data(map);
    }
    @GetMapping("teacherInfo/{id}")
    public ResultJson getTeacherInfo(@PathVariable String id){
        Teacher teacherServiceById = teacherService.getById(id);
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        List<Course> list = courseService.list(queryWrapper);
        return ResultJson.ok().data("teacher",teacherServiceById).data("courseList",list);

    }


}
