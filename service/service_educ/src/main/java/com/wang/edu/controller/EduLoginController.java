package com.wang.edu.controller;

import com.wang.commonutil.ResultJson;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/edu/user")
@CrossOrigin
public class EduLoginController {

        @PostMapping("login")
        public ResultJson login(){
        return ResultJson.ok().data("token","admin");
        }
        @GetMapping("info")
    public ResultJson info(){
            return ResultJson.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
}}
