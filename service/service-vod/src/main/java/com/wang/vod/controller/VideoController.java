package com.wang.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutil.ResultJson;
import com.wang.vod.Utils.ConstantPropertiesUtil;
import com.wang.vod.Utils.InitObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin //跨域
@RestController
@RequestMapping("/vod/video")
public class VideoController {
    @GetMapping("get-play-auth/{videoId}")
    public ResultJson getVideoPlayAuth(@PathVariable("videoId") String videoId) throws
            Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client =InitObject.initVodClient(accessKeyId,accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return ResultJson.ok().message("获取凭证成功").data("playAuth", playAuth);
    }
}
