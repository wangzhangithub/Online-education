package com.wang.ucenter.api;

import com.google.gson.Gson;
import com.wang.commonutil.GuliException;
import com.wang.commonutil.JwtUtils;
import com.wang.ucenter.bean.Member;
import com.wang.ucenter.service.MemberService;
import com.wang.ucenter.utils.ConstantPropertiesUtil;
import com.wang.ucenter.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    MemberService memberService;
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {
//得到授权临时票据code
        try{
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            String result= HttpClientUtils.get(accessTokenUrl);
            Gson gson=new Gson();
            HashMap hashMap = gson.fromJson(result, HashMap.class);
            String access_token=(String) hashMap.get("access_token");
            String openid=(String)hashMap.get("openid");

            Member member=memberService.getMemberByOpenId(openid);
            if(member==null){
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String useInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String userInfo = HttpClientUtils.get(useInfoUrl);
                HashMap userMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userMap.get("nickname");
                String headImg = (String)userMap.get("headimgurl");
                member = new Member();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headImg);
                memberService.save(member);
            }
            String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
//存入cookie
//CookieUtils.setCookie(request, response, "guli_jwt_token", token);
//因为端口号不同存在蛞蝓问题，cookie不能跨域，所以这里使用url重写
            return "redirect:http://localhost:3000?token=" + token;
        }catch (Exception e){
            throw new GuliException(20001,"登录失败");
        }

    }
    @GetMapping("login")
    public String genQrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置

        System.out.println("state = " + state);
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    } }
