package com.wang.edu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.edu.bean.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.edu.bean.subject.OneSubject;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-24
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    void selectList(QueryWrapper<OneSubject> oneWrapper);
}
