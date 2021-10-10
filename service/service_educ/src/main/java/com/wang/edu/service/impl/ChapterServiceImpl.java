package com.wang.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.edu.bean.Chapter;
import com.wang.edu.bean.Video;
import com.wang.edu.bean.chapter.ChapterVo;
import com.wang.edu.bean.chapter.VideoVo;
import com.wang.edu.exceptionHandler.GuliException;
import com.wang.edu.mapper.ChapterMapper;
import com.wang.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    public VideoService videoService;
    @Override
    public List<ChapterVo> getChapterAndVideo(String courseId) {
        QueryWrapper<Chapter> chapterQueryWrapperWrapper=new QueryWrapper<>();
        chapterQueryWrapperWrapper.eq("course_id",courseId);
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapperWrapper);
        QueryWrapper<Video> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<Video> videoList = videoService.list(videoQueryWrapper);
        List<ChapterVo> finalChapterVo=new ArrayList<>();
        for(int i=0;i<chapterList.size();i++){
            ChapterVo chapterVo=new ChapterVo();
            Chapter chapter=chapterList.get(i);
            BeanUtils.copyProperties(chapter,chapterVo);
            List<VideoVo> videoListTemp=new ArrayList<>();
            for (int m=0;m<videoList.size();m++){
                if(videoList.get(m).getChapterId().equals(chapter.getId())){
                    VideoVo videoVo=new VideoVo();
                    Video video=videoList.get(m);
                    BeanUtils.copyProperties(video,videoVo);
                    videoListTemp.add(videoVo);
                }
            }
            chapterVo.setChildren(videoListTemp);
            finalChapterVo.add(chapterVo);
        }
        return finalChapterVo;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrapper);
        if (count>0){
            throw new GuliException(20001,"请先删除该章节节点");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }
}
