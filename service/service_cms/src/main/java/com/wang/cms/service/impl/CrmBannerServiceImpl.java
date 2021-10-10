package com.wang.cms.service.impl;

import com.wang.cms.bean.CrmBanner;
import com.wang.cms.mapper.CrmBannerMapper;
import com.wang.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-30
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Cacheable(value = "bannerList",key="'selectAllBanner'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> list = baseMapper.selectList(null);

        return list;
    }
}
