package com.wang.statistic.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutil.ResultJson;
import com.wang.statistic.Client.UserClient;
import com.wang.statistic.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-07
 */
@RestController
@RequestMapping("/statistic")
@CrossOrigin
public class StatisticsDailyController {

@Autowired
    StatisticsDailyService statisticsDailyService;
@PostMapping("{day}")
    public ResultJson saveRegisterCount(@PathVariable String day){

    statisticsDailyService.saveRegisterCount(day);
    return ResultJson.ok();
}
    @GetMapping("show-chart/{begin}/{end}/{type}")
    public ResultJson showChart(@PathVariable String begin, @PathVariable String
            end, @PathVariable String type){
        Map<Object, Object> map = statisticsDailyService.getChartData(begin, end, type);
        return ResultJson.ok().data(map);
    }
}

