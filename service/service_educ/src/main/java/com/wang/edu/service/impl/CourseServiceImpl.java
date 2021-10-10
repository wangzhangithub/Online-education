package com.wang.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.edu.bean.Chapter;
import com.wang.edu.bean.Course;
import com.wang.edu.bean.CourseDescription;
import com.wang.edu.bean.Video;
import com.wang.edu.bean.vo.CourseInfoVo;
import com.wang.edu.bean.vo.CoursePublishVo;
import com.wang.edu.bean.vo.CourseQueryVo;
import com.wang.edu.bean.vo.CourseWebVo;
import com.wang.edu.client.VodClient;
import com.wang.edu.exceptionHandler.GuliException;
import com.wang.edu.mapper.CourseMapper;
import com.wang.edu.service.ChapterService;
import com.wang.edu.service.CourseDescriptionService;
import com.wang.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    CourseDescriptionService descriptionService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    VideoService videoService;
    @Autowired
    VodClient vodClient;
    @Override
    public String saveCourseInfoVo(CourseInfoVo courseInfoVo) {
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert = baseMapper.insert(course);
        if (insert==0){
            throw new GuliException(20001,"添加失败");
        }
        String id = course.getId();
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(id);
        descriptionService.save(courseDescription);
        return id;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        System.out.println(courseId);
        Course course = baseMapper.selectById(courseId);
        System.out.println(course);
        CourseInfoVo courseInfoVo= new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        courseInfoVo.setDescription(descriptionService.getById(courseId).getDescription());
        return courseInfoVo;
    }
    public CourseWebVo getCourseWebVo(String courseId){
        return baseMapper.selectInfoWebById(courseId);
    }


    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //更新course
        String id = courseInfoVo.getId();
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        baseMapper.updateById(course);

        //更新简介表
        CourseDescription courseDescription=descriptionService.getById(id);
        courseDescription.setDescription(courseInfoVo.getDescription());
        descriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean removeCourseById(String id) {
        //删除所有视频
        QueryWrapper<Video> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", id);

        List<Video> videoList = videoService.list(queryWrapper1);
        List<String> videoIdList=new ArrayList<>();
        for(int i=0;i<videoList.size();i++){
            Video video = videoList.get(i);
            String videoSourceId=video.getVideoSourceId();
            videoIdList.add(videoSourceId);
        }
        System.out.println(videoIdList);
        if (videoIdList.size()>0){
            vodClient.removeMoreVideo(videoIdList);
        }
        //删除所有小节
        QueryWrapper<Video> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        videoService.remove(videoQueryWrapper);
        //删除章节
        QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        chapterService.remove(queryWrapper);
        //删除简介
        descriptionService.removeById(id);
        //
        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public Map<Object, Object> getCoursePageFront(Page<Course> coursePage, CourseQueryVo courseQuery) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id",
                    courseQuery.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
    }

        baseMapper.selectPage(coursePage, queryWrapper);
            List<Course> records = coursePage.getRecords();
            long current = coursePage.getCurrent();
            long pages = coursePage.getPages();
            long size = coursePage.getSize();
            long total = coursePage.getTotal();
            boolean hasNext = coursePage.hasNext();
            boolean hasPrevious = coursePage.hasPrevious();
            Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map; }
}
