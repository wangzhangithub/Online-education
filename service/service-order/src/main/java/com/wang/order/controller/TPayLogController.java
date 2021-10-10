package com.wang.order.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutil.ResultJson;
import com.wang.order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-06
 */
@RestController
@RequestMapping("/order/pay")
@CrossOrigin
public class TPayLogController {
    @Autowired
    private TPayLogService payService;
    //生成二维码
    @GetMapping("/createNative/{orderNo}")
    public ResultJson createNative(@PathVariable String orderNo) {
        Map<Object,Object> map = payService.createNative(orderNo);
        return ResultJson.ok().data(map);
    }
    @GetMapping("/queryPayStatus/{orderNo}")
    public ResultJson queryPayStatus(@PathVariable String orderNo) {
//调用查询接口
        Map<String, String> map = payService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return ResultJson.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
//更改订单状态
            payService.updateOrderStatus(map);
            return ResultJson.ok().message("支付成功");
        }
        return ResultJson.ok().code(25000).message("支付中");
    }
}


