package com.wang.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutil.JwtUtils;
import com.wang.commonutil.ResultJson;
import com.wang.edu.bean.Course;
import com.wang.edu.bean.chapter.ChapterVo;
import com.wang.edu.bean.vo.CourseInfoVo;
import com.wang.edu.bean.vo.CourseQueryVo;
import com.wang.edu.bean.vo.CourseWebVo;
import com.wang.edu.client.OrderClient;
import com.wang.edu.service.ChapterService;
import com.wang.edu.service.CourseService;
import com.wang.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/edu/courseFront")
@CrossOrigin
@RestController
public class CourseFrontController {
    @Autowired
    CourseService courseService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    OrderClient orderClient;
    @PostMapping("courseList/{current}/{limit}")
    public ResultJson getCourseByCondition(@PathVariable long current,
                                           @PathVariable long limit,
                                           @RequestBody(required=false) CourseQueryVo courseQueryVo){
        Page<Course> coursePage=new Page<>(current,limit);
        Map<Object,Object> map=courseService.getCoursePageFront(coursePage,courseQueryVo);
        return ResultJson.ok().data(map);

    }
    @GetMapping("courseInfo/{id}")
    public ResultJson getCourseInfo(@PathVariable String id, HttpServletRequest request){
        CourseWebVo courseWebVo = courseService.getCourseWebVo(id);
        List<ChapterVo> chapterAndVideo = chapterService.getChapterAndVideo(id);

        boolean isBuy = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request),id);
        return ResultJson.ok().data("courseWebVo",courseWebVo).data("chapterAndVideo",chapterAndVideo).data("isBuy",isBuy);
    }
    @GetMapping("courseWebVo/{id}")
    public CourseWebVo getCourseWebVo(@PathVariable String id){
        CourseWebVo courseWebVo = courseService.getCourseWebVo(id);
        return courseWebVo;
    }

}
