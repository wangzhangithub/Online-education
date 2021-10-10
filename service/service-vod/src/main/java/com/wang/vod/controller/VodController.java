package com.wang.vod.controller;

import com.wang.commonutil.ResultJson;
import com.wang.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod")
@CrossOrigin
public class VodController {
    @Autowired
    VodService vodService;
    @PostMapping("uploadVideo")
    public ResultJson uploadVideo(MultipartFile file){
        String videoId=vodService.uploadVideo(file);
        return ResultJson.ok().data("videoId",videoId);
    }
    @DeleteMapping("removeVideo/{id}")
    public ResultJson removeVideo(@PathVariable String id){
        vodService.removeVideo(id);
        return ResultJson.ok();
    }
    @DeleteMapping("removeMoreVideo")
    public ResultJson removeMoreVideo(@RequestParam("videoList") List<String> videoIdList){
        vodService.removeMoreVideo(videoIdList);
        return ResultJson.ok();
    }

}
