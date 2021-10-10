package com.wang.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.edu.bean.Subject;
import com.wang.edu.bean.excel.ExcelSubjectData;
import com.wang.edu.service.SubjectService;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {
    public SubjectService subjectService;

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {
    }

    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {
        Subject existOneSubject = this.existOneSubject(subjectService,subjectData.getOneSubjectName());
        if(existOneSubject == null) {//没有相同的
            existOneSubject = new Subject();
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }
//获取一级分类id值
        String pid = existOneSubject.getId();
//添加二级分类
        Subject existTwoSubject =
                this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new Subject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
    private Subject existOneSubject(SubjectService subjectService,String name) {
        QueryWrapper<Subject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("title",name)
                    .eq("parent",0);
        return subjectService.getOne(queryWrapper);
    }
    private Subject existTwoSubject(SubjectService subjectService,String name,String pid) {
        QueryWrapper<Subject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("title",name)
                .eq("parent",pid);
        return subjectService.getOne(queryWrapper);
    }
    }

