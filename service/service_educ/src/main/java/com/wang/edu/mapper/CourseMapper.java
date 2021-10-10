package com.wang.edu.mapper;

import com.wang.edu.bean.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.edu.bean.vo.CoursePublishVo;
import com.wang.edu.bean.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
public interface CourseMapper extends BaseMapper<Course> {
    public CoursePublishVo selectCoursePublishVoById(String id);
    CourseWebVo selectInfoWebById(String courseId);
}
