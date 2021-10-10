package com.wang.commonutil;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data

public class ResultJson {
    private boolean isSuccess;
    private Integer code;
    private String massage;
    private Map<Object,Object> data = new HashMap<>();

    @Override
    public String toString() {
        return "ResultJson{" +
                "isSuccess=" + isSuccess +
                ", code=" + code +
                ", massage='" + massage + '\'' +
                ", data=" + data +
                '}';
    }


    private ResultJson(){}

    public static ResultJson ok(){
        ResultJson resultJson=new ResultJson();
        resultJson.setSuccess(true);
        resultJson.setCode(ResultCode.SUCCESS);
        resultJson.setMassage("成功");
        return resultJson;
    }
    public static ResultJson error(){
        ResultJson resultJson=new ResultJson();
        resultJson.setSuccess(false);
        resultJson.setCode(ResultCode.ERROR);
        resultJson.setMassage("失败");
        return resultJson;
    }
    public ResultJson success(boolean isSuccess){
        this.setSuccess(isSuccess);
        return this;
    }
    public ResultJson message(String message){
        this.setMassage(message);
        return this;
    }
    public ResultJson code(Integer code){
        this.setCode(code);
        return this;
    }
    public  ResultJson data(Object key,Object value){

        this.data.put(key,value);
        return this;}
    public ResultJson data(Map<Object,Object>map){
        this.setData(map);
        return this;
    }
    
}
