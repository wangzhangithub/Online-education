package com.wang.edu.client;

import com.wang.commonutil.ResultJson;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public ResultJson removeVideo(String id) {

        return ResultJson.error().message("删除视频超时");
    }

    @Override
    public ResultJson removeMoreVideo(List<String> videoIdList) {

        return ResultJson.error().message("删除多个视频超时");
    }
}
