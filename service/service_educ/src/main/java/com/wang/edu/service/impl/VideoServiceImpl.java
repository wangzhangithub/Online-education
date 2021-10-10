package com.wang.edu.service.impl;

import com.wang.edu.bean.Video;
import com.wang.edu.mapper.VideoMapper;
import com.wang.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-25
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
