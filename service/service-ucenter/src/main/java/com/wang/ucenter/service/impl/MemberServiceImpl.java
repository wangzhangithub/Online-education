package com.wang.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutil.GuliException;
import com.wang.commonutil.JwtUtils;
import com.wang.commonutil.MD5;
import com.wang.commonutil.ResultJson;
import com.wang.ucenter.bean.Member;
import com.wang.ucenter.bean.vo.LoginVo;
import com.wang.ucenter.bean.vo.RegisterVo;
import com.wang.ucenter.mapper.MemberMapper;
import com.wang.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-02
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public String login(LoginVo login) {
        String email = login.getEmail();
        String password = login.getPassword();
//校验参数
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"error");
        }
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("email", email));
        if (null==member){
            throw new GuliException(20001,"error");
        }
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001,"error");
        }
//校验是否被禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"error");
        }
//使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

@Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String email = registerVo.getEmail();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
//校验参数
        if(StringUtils.isEmpty(email) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"error");
        }
        String getCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(getCode)){
            throw new GuliException(20001,"error");
        }
        Member member1 = baseMapper.selectOne(new QueryWrapper<Member>().eq("email", email));
        if (null!=member1){
            throw new GuliException(20001,"error");
        }
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail( registerVo.getEmail());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
    }

    @Override
    public LoginVo getLoginInfo(String memberId) {
        Member member = baseMapper.selectById(memberId);
        LoginVo loginInfoVo = new LoginVo();
        BeanUtils.copyProperties(member, loginInfoVo);
        return loginInfoVo;
    }

    @Override
    public Member getMemberByOpenId(String openid) {
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public int countRegister(String day) {
        return  baseMapper.countRegister(day);

    }
}
