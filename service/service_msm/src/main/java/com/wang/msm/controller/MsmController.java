package com.wang.msm.controller;

import com.wang.commonutil.RandomUtil;
import com.wang.commonutil.ResultJson;
import com.wang.msm.servive.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/msm")
public class MsmController {
    @Autowired
    MsmService msmService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @PostMapping("sendCode/{phone}")
    public ResultJson sendCode(@PathVariable String phone) {

        Object getCode = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(getCode)) {
            return ResultJson.ok();
        }
        String code = RandomUtil.getFourBitRandom();
        String s = msmService.sendAuthCodeEmail(phone, code);
        if (!StringUtils.isEmpty(s)) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return ResultJson.ok();
        } else {
            return ResultJson.error().message("发送失败");
        }
    }
}
