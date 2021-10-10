package com.wang.ucenter.service;

import com.wang.ucenter.bean.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.ucenter.bean.vo.LoginVo;
import com.wang.ucenter.bean.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-02
 */
public interface MemberService extends IService<Member> {

    public String login(LoginVo login);

    public void register(RegisterVo registerVo);

    public LoginVo getLoginInfo(String memberId);

    Member getMemberByOpenId(String openid);

    int countRegister(String day);
}
