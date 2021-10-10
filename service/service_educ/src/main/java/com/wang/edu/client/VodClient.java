package com.wang.edu.client;

import com.wang.commonutil.ResultJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("/vod/removeVideo/{id}")
    public ResultJson removeVideo(@PathVariable("id") String id);
    @DeleteMapping("/vod/removeMoreVideo")
    public ResultJson removeMoreVideo(@RequestParam("videoList") List<String> videoIdList);

}
