package com.wang.order.service;

import com.wang.order.bean.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-05
 */
public interface TOrderService extends IService<TOrder> {

    String getOrder(String courseId, String memberIdByJwtToken);
}
