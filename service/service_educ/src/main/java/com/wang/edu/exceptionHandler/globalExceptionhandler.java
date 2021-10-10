package com.wang.edu.exceptionHandler;

import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutil.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
@Slf4j
public class globalExceptionhandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultJson error(Exception e){
        log.error("网络错误");
        e.printStackTrace();
        return ResultJson.error().message("网络开小差了");
    }
}
