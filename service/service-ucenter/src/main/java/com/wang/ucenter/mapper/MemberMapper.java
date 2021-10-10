package com.wang.ucenter.mapper;

import com.wang.ucenter.bean.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Mr.wang
 * @since 2021-10-02
 */
public interface MemberMapper extends BaseMapper<Member> {

    int countRegister(String day);
}
