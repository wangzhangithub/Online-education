package com.wang.edu.service;

import com.wang.edu.bean.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.edu.bean.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-24
 */
public interface SubjectService extends IService<Subject> {
    public void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getSubjectList();
}
