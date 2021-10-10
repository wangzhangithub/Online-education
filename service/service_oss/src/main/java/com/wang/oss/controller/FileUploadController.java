package com.wang.oss.controller;

import com.wang.commonutil.ResultJson;
import com.wang.oss.Service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss")
@CrossOrigin
public class FileUploadController {
    @Autowired
    private OssService ossService;
    @PostMapping("upload")
    public ResultJson uploadFile(@RequestParam("file")MultipartFile file){
        String url=ossService.uploadFileAvatar(file);
        return ResultJson.ok().data("url",url);
    }
}
