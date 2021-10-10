package com.wang.ucenter.bean.vo;

import lombok.Data;

@Data
public class RegisterVo {
    String email;
    String password;
    String code;
    private String nickname;
}
