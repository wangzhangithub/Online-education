package com.wang.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.edu.bean.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-14
 */
public interface TeacherService extends IService<Teacher> {

    Map<Object, Object> PageListWeb(Page<Teacher> teacherPage);
}
