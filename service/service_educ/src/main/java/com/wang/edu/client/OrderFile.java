package com.wang.edu.client;

import org.springframework.stereotype.Component;

@Component
public class OrderFile implements OrderClient {
    @Override
    public boolean isBuyCourse(String memberid, String id) {
        System.out.println("查询超时");
        return false;
    }
}
