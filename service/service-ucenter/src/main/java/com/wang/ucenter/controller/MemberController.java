package com.wang.ucenter.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.sun.org.apache.regexp.internal.RE;
import com.wang.commonutil.GuliException;
import com.wang.commonutil.JwtUtils;
import com.wang.commonutil.ResultJson;
import com.wang.commonutil.vo.UcenterMemberOrder;
import com.wang.ucenter.bean.Member;
import com.wang.ucenter.bean.vo.LoginVo;
import com.wang.ucenter.bean.vo.RegisterVo;
import com.wang.ucenter.service.MemberService;
import com.wang.ucenter.service.impl.MemberServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-02
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    MemberService memberService;
    @PostMapping("login")
    public ResultJson login(@RequestBody LoginVo login){
       String token= memberService.login(login);
       return ResultJson.ok().data("token",token);
    }
    @PostMapping("register")
    public  ResultJson register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return ResultJson.ok();
    }
    @GetMapping("auth/getLoginInfo")
    public ResultJson getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            System.out.println(memberId);
            Member byId = memberService.getById(memberId);
            return ResultJson.ok().data("item", byId);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }

    }
    @GetMapping("getMemberById/{id}")
    public UcenterMemberOrder getMemberById(@PathVariable String id){
        Member member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
    @GetMapping("countRegister/{day}")
    public ResultJson countRegister(@PathVariable String day){
        int count=memberService.countRegister(day);
        return ResultJson.ok().data("count",count);
    }

}

