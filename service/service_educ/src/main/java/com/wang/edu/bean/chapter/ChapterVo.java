package com.wang.edu.bean.chapter;

import com.wang.edu.bean.subject.TwoSubject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data


public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
