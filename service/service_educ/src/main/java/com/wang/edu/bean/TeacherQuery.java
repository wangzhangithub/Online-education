package com.wang.edu.bean;

import lombok.Data;

import java.util.Date;
@Data
public class TeacherQuery {
    private String name;
    private Integer level;
    private Date begin;
    private Date end;
}
