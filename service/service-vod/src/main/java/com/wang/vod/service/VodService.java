package com.wang.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


public interface VodService {
    public String uploadVideo(MultipartFile file);
    public void removeVideo(String id);
    public void removeMoreVideo(List<String> ids);
}
