package com.wang.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutil.JwtUtils;
import com.wang.commonutil.ResultJson;
import com.wang.order.bean.TOrder;
import com.wang.order.service.TOrderService;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-05
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class TOrderController {
    @Autowired
    TOrderService orderService;
    @PostMapping("save/{courseId}")
    public ResultJson save(@PathVariable String courseId, HttpServletRequest request){
        String orderId=orderService.getOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return ResultJson.ok().data("orderNo",orderId);


    }
    @GetMapping("getOrder/{orderId}")
    public ResultJson get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return ResultJson.ok().data("item", order);
    }
    @GetMapping("isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable String memberid,
                               @PathVariable String id) {
//订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<TOrder>().eq("member_id",
                memberid).eq("course_id", id).eq("status", 1));
        return count > 0;
    }
}

