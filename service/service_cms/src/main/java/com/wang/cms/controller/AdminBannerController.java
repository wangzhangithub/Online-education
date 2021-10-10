package com.wang.cms.controller;

import com.wang.cms.bean.CrmBanner;
import com.wang.cms.service.CrmBannerService;
import com.wang.commonutil.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/adminBanner")
@CrossOrigin
public class AdminBannerController {
    @Autowired
    CrmBannerService crmBannerService;
    @GetMapping("get/{id}")
    public ResultJson getById(@PathVariable String id){
        CrmBanner byId = crmBannerService.getById(id);
        return ResultJson.ok().data("banner",byId);
    }
    @PostMapping("save")
    public ResultJson save(@RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.save(crmBanner);
        return ResultJson.ok();
    }
    @PutMapping("update")
    public ResultJson update(@RequestBody CrmBanner crmBanner){
        boolean b = crmBannerService.updateById(crmBanner);
        return ResultJson.ok();
    }
    @DeleteMapping("delete/{id}")
    public  ResultJson deleteById(@PathVariable String id){
        boolean b = crmBannerService.removeById(id);
        return ResultJson.ok();
    }

}
