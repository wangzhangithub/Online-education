package com.wang.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.wang.vod.Utils.ConstantPropertiesUtil;
import com.wang.vod.Utils.GuliException;
import com.wang.vod.Utils.InitObject;
import com.wang.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            request.setStorageLocation("outin-1c3bd64a203d11ecaa6300163e00b174.oss-cn-shanghai.aliyuncs.com");
            request.setApiRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" +
                        response.getCode() + ", message：" + response.getMessage();
                System.out.println(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;
        }catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }


}

    @Override
    public void removeVideo(String id) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request=new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }


    }

    @Override
    public void removeMoreVideo(List<String> ids) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request=new DeleteVideoRequest();
            String str=org.apache.commons.lang.StringUtils.join(ids.toArray(),",");
            System.out.println(str);
            request.setVideoIds(str);
            client.getAcsResponse(request);
//            DeleteVideoResponse response = client.getAcsResponse(request);
//            System.out.print("RequestId = " + response.getRequestId() + "\n");
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
