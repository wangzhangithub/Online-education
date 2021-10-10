package com.wang.order.service.impl;

import com.wang.commonutil.OrderNoUtil;
import com.wang.commonutil.vo.CourseWebVo;
import com.wang.commonutil.vo.UcenterMemberOrder;
import com.wang.order.bean.TOrder;
import com.wang.order.client.EduClient;
import com.wang.order.client.UserClient;
import com.wang.order.mapper.TOrderMapper;
import com.wang.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-05
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    UserClient userClient;
    @Autowired
    EduClient eduClient;
    @Override
    public String getOrder(String courseId, String memberIdByJwtToken) {
        CourseWebVo courseWebVo = eduClient.getCourseWebVo(courseId);
        UcenterMemberOrder memberById = userClient.getMemberById(memberIdByJwtToken);
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseWebVo.getTitle());
        order.setCourseCover(courseWebVo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseWebVo.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(memberById.getMobile());
        order.setNickname(memberById.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();

    }
}
