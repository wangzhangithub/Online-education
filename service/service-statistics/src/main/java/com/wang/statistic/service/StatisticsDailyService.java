package com.wang.statistic.service;

import com.wang.statistic.bean.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-07
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void saveRegisterCount(String day);

    Map<Object, Object> getChartData(String begin, String end, String type);
}
