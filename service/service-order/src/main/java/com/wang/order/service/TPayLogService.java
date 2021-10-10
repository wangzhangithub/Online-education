package com.wang.order.service;

import com.wang.order.bean.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-06
 */
public interface TPayLogService extends IService<TPayLog> {

    Map<Object, Object> createNative(String orderNo);

    void updateOrderStatus(Map<String, String> map);

    Map<String, String> queryPayStatus(String orderNo);
}
