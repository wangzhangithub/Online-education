package com.wang.order.client;

import com.wang.commonutil.vo.CourseWebVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    @GetMapping("/edu/courseFront/courseWebVo/{id}")
    public CourseWebVo getCourseWebVo(@PathVariable("id") String id);
}
