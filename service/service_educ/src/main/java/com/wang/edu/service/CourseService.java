package com.wang.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.edu.bean.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.edu.bean.vo.CourseInfoVo;
import com.wang.edu.bean.vo.CoursePublishVo;
import com.wang.edu.bean.vo.CourseQueryVo;
import com.wang.edu.bean.vo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
public interface CourseService extends IService<Course> {

    public String saveCourseInfoVo(CourseInfoVo courseInfoVo);

    public CourseInfoVo getCourseInfo(String courseId);

    public void updateCourseInfo(CourseInfoVo courseInfoVo);


    public  CoursePublishVo getCoursePublishVoById(String id);

    public boolean removeCourseById(String id);

    Map<Object,Object> getCoursePageFront(Page<Course> coursePage, CourseQueryVo courseQueryVo);
    public CourseWebVo getCourseWebVo(String courseId);
}
