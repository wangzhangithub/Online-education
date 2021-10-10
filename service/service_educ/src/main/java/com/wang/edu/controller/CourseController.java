package com.wang.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.Course;
import com.wang.edu.bean.CourseQuery;
import com.wang.edu.bean.Teacher;
import com.wang.edu.bean.TeacherQuery;
import com.wang.edu.bean.vo.CourseInfoVo;
import com.wang.edu.bean.vo.CoursePublishVo;
import com.wang.edu.service.CourseService;
import com.wang.edu.service.TeacherService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @PostMapping("addCourseInfoVo")
    public ResultJson addCourseInfoVo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfoVo(courseInfoVo);
        return ResultJson.ok().data("id",id);
    }
    @GetMapping("getCourseInfo/{courseId}")
    public ResultJson getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return ResultJson.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public ResultJson updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return ResultJson.ok();
    }
    @GetMapping("course-publish-info/{id}")
    public ResultJson getCoursePublishVoById(
            @PathVariable String id){
        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return ResultJson.ok().data("item", courseInfoForm);
    }
    @PostMapping("updateStatus/{id}")
    public ResultJson updateStatus(@PathVariable String id){
        Course course=new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return ResultJson.ok();
    }

    @PostMapping("pageCourseCondition/{current}/{limit}")
    public ResultJson CourseQuery(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> coursePage=new Page<>(current,limit);
        String name = courseQuery.getName();
        String status = courseQuery.getStatus();
        String teacherName=courseQuery.getTeacherName();
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            courseQueryWrapper.like("title",name);
        }
        if(!StringUtils.isEmpty(status)){
            courseQueryWrapper.eq("status",status);
        }
        if(!StringUtils.isEmpty(teacherName)){
            QueryWrapper<Teacher> teacherQueryWrapper=new QueryWrapper<>();
            teacherQueryWrapper.eq("name",teacherName);
            Teacher one = teacherService.getOne(teacherQueryWrapper);
            String teacherId = one.getId();
            courseQueryWrapper.eq("teacher_id",teacherId);
        }
        courseQueryWrapper.orderByDesc("view_count");
        courseService.page(coursePage,courseQueryWrapper);
        long total = coursePage.getTotal();
        List<Course> records = coursePage.getRecords();
        return ResultJson.ok().data("total",total).data("items",records);
    }
    @DeleteMapping("{id}")
    public ResultJson removeById(
            @PathVariable String id){
        boolean result = courseService.removeCourseById(id);
        if(result){
            return ResultJson.ok();
        }else{
            return ResultJson.error().message("删除失败");
        }
    }
}

