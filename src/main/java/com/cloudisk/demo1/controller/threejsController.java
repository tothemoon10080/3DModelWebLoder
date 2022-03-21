package com.cloudisk.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class threejsController {
    
@RequestMapping("/gltf")
public String show3d(@RequestParam String modelurl,ModelMap mm){
    mm.put("modelurl",modelurl);

    return "gltf";
}

@RequestMapping("/mmd")
public String show3d1(@RequestParam("modelurl") String modelurl, @RequestParam(value = "vmburl", defaultValue = "http://127.0.0.1:9018/mmd/vmds/shuji.vmd" , required = false) String vmpurl ,ModelMap mm){
    mm.put("modelurl", modelurl); 
    mm.put("vmburl", vmpurl); 
    return "mmd";
}
}
