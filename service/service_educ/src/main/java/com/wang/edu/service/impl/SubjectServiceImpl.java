package com.wang.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.edu.bean.Subject;
import com.wang.edu.bean.excel.ExcelSubjectData;
import com.wang.edu.bean.subject.OneSubject;
import com.wang.edu.bean.subject.TwoSubject;
import com.wang.edu.listener.SubjectExcelListener;
import com.wang.edu.mapper.SubjectMapper;
import com.wang.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-24
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getSubjectList() {
        QueryWrapper<Subject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", 0);
        oneWrapper.orderByDesc("sort", "id");
        List<Subject> oneSubjectList = baseMapper.selectList(oneWrapper);

        QueryWrapper<Subject> twoWrapper = new QueryWrapper<>();
        oneWrapper.ne("parent_id", 0);
        oneWrapper.orderByDesc("sort", "id");
        List<Subject> twoSubjectList = baseMapper.selectList(twoWrapper);

        List<OneSubject> finalSubjectList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            Subject one = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(one, oneSubject);


            List<TwoSubject> finalTwoSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                Subject two = twoSubjectList.get(m);
                if(two.getParentId().equals(one.getId())){
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(two, twoSubject);
                finalTwoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(finalTwoSubjectList);
            finalSubjectList.add(oneSubject);

        }
            return finalSubjectList;
    }
}
