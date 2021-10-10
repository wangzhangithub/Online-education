package com.wang.statistic.Client;

import com.wang.commonutil.ResultJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface UserClient {
    @GetMapping("/ucenter/member/countRegister/{day}")
    public ResultJson countRegister(@PathVariable("day") String day);
}
