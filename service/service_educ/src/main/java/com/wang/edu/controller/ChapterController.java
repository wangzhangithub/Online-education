package com.wang.edu.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.Chapter;
import com.wang.edu.bean.chapter.ChapterVo;
import com.wang.edu.exceptionHandler.GuliException;
import com.wang.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {
@Autowired
    public ChapterService chapterService;
    @PostMapping("getChapterAndVideo/{courseId}")
    public ResultJson getChapterAndVideo(@PathVariable String courseId){
        List<ChapterVo> chapterAndVideo = chapterService.getChapterAndVideo(courseId);
        return ResultJson.ok().data("chapterAndVideo",chapterAndVideo);
    }
    @PostMapping("addChapter")
    public ResultJson addChapter(@RequestBody Chapter chapter){
        boolean save = chapterService.save(chapter);
        return ResultJson.ok();

    }
    @GetMapping("getChapterInfo/{chapterId}")
    public ResultJson getChapterInfo(@PathVariable String chapterId) {
        Chapter Chapter = chapterService.getById(chapterId);
        return ResultJson.ok().data("chapter",Chapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public ResultJson updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return ResultJson.ok();
    }
    @DeleteMapping("{chapterId}")
    public ResultJson deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return ResultJson.ok();
        } else {
            return ResultJson.error();
        }

    }

}

