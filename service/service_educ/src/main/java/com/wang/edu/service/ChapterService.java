package com.wang.edu.service;

import com.wang.edu.bean.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.edu.bean.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
public interface ChapterService extends IService<Chapter> {

    public List<ChapterVo> getChapterAndVideo(String courseId);

    public boolean deleteChapter(String chapterId);
}
