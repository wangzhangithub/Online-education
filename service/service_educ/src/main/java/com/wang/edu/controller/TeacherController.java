package com.wang.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.*;
import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.Teacher;
import com.wang.edu.bean.TeacherQuery;
import com.wang.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-14
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping(value = "/edu/teacher")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @ApiOperation(value = "讲师列表")
    @GetMapping("queryAll")
    public ResultJson queryAllTeacher(){
        List<Teacher> list = teacherService.list(null);
        return ResultJson.ok().data("items",list);

    }

    @DeleteMapping("deleteById/{id}")
    public ResultJson removeTeacher(@PathVariable String id){
        boolean b = teacherService.removeById(id);
        System.out.println(b);
        if(b){
            return ResultJson.ok();
        }else {
            return ResultJson.error();
        }
    }
    @PostMapping("pageTeacher/{current}/{limit}")
    public ResultJson pageListTeacher(@PathVariable long current,
                                      @PathVariable long limit){
        Page<Teacher> teacherPage=new Page<>(current,limit);
        teacherService.page(teacherPage,null);
        long total=teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return ResultJson.ok().data("total",total).data("rows",records);
    }
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public ResultJson TeacherQuery(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> teacherPage=new Page<>(current,limit);
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        Date begin = teacherQuery.getBegin();
        Date end = teacherQuery.getEnd();
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        queryWrapper.orderByDesc("gmt_create");

        teacherService.page(teacherPage,queryWrapper);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return ResultJson.ok().data("total",total).data("items",records);

    }
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public ResultJson save(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if(save){
            return ResultJson.ok();
        }else{
            return  ResultJson.error();
        }
    }
    @GetMapping("getTeacher/{id}")
    public ResultJson getTeaById(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return ResultJson.ok().data("teacher",teacher);
    }
    @PostMapping("updateTeacher")
    public ResultJson updateTeaById(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if(b){
            return ResultJson.ok();
        }else{
            return  ResultJson.error();
        }
    }
}

