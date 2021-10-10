package com.wang.cms.controller;


import com.wang.cms.bean.CrmBanner;
import com.wang.cms.service.CrmBannerService;
import com.wang.commonutil.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Mr.wang
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/cms/banner")
@CrossOrigin
public class CrmBannerController {
@Autowired
    CrmBannerService crmBannerService;
@GetMapping("getAllBanner")
    public ResultJson getAllBanner(){
        List<CrmBanner> list=crmBannerService.selectAllBanner();
        return ResultJson.ok().data("list",list);
    }
}

