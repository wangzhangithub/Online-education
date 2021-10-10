package com.wang.edu.controller;


import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.Video;
import com.wang.edu.client.VodClient;
import com.wang.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {
    @Autowired
    public VideoService videoService;
    @Autowired
    private VodClient vodClient;
    @PostMapping("addVideo")
    public ResultJson addVideo(@RequestBody Video video){
        videoService.save(video);
        String id=video.getId();
        return ResultJson.ok().data("id",id);
    }
    @PostMapping("updateVideo")
    public ResultJson updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return ResultJson.ok();
    }
    @GetMapping("getVideo/{videoId}")
    public ResultJson getVideo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        return ResultJson.ok().data("video",video);
    }

    @DeleteMapping("{videoId}")
    public ResultJson deleteVideo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideo(videoSourceId);
        }
        videoService.removeById(videoId);
        return ResultJson.ok();
    }
}

