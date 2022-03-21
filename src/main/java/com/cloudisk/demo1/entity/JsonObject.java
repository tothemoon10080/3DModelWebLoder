package com.cloudisk.demo1.entity;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class JsonObject {
    
    
    public String toJSONString(List<filentity> listOfilentity){       
        String jsonOutput= JSON.toJSONString(listOfilentity);
        return jsonOutput;
    }


    
}
