package com.cloudisk.demo1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.cloudisk.demo1.entity.JsonObject;
import com.cloudisk.demo1.entity.filentity;
import com.cloudisk.demo1.service.FileService;
import com.cloudisk.demo1.service.impl.fileserviceimpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

/**
 * @Description
 * @Author sgl
 * @Date 2018-05-15 14:04
 */



@RestController
public class fileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(fileController.class);


    @Value("${datasource.file_save_path}")
    private String fpath;

    @Value("${datasource.file_http_url}")
    private String furl ;
    

    @Resource
        private FileService fileService;

    @RequestMapping("/mkdirs")
    public String mkdirs(@RequestParam String fileurl){
        String filePath = fileurl.replace(furl, fpath);
        try {
            fileService.mkdirs(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return "失败"+e.toString();
            
        }
        return "完成";
    }


     
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file , @RequestParam("type") String type) {
        String fileName = file.getOriginalFilename();
        String fend = fileName.substring(fileName.lastIndexOf("."));

        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }else if(type.equals(".gltf")){
            if(!(fend.equals(".glb") || fend.equals(".gltf") || fend.equals(".zip"))){
                return "gltf 必须 glb gltf zip";
            }
        }else if(type.equals("mmd")){
            if(!fend.equals(".zip")){
                return "mmd 必须 zip";
            }
        }

        
        File dest = new File(fpath +type +"/" + fileName);
        

        try {
            file.transferTo(dest);

                if(type.equals("mmd")){
                    try {
                        fileService.toUnZip(dest.getPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "上传成功 解压失败";
                    }    
                       
                }
            return "上传成功";
        } catch ( IOException e ) {
            e.printStackTrace();
            return "上传失败";
        }
        
    }

    // @GetMapping("/multiUpload")
    // public String multiUpload() {
    //     return "multiUpload";
    // }

    // @PostMapping("/multiUpload")
    // @ResponseBody
    // public String multiUpload(HttpServletRequest request) {
    //     List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
    //     String filePath = "C:/clouddiskdemo/";
    //     for (int i = 0; i < files.size(); i++) {
    //         MultipartFile file = files.get(i);
    //         if (file.isEmpty()) {
    //             return "上传第" + (i++) + "个文件失败";
    //         }
    //         String fileName = file.getOriginalFilename();

    //         File dest = new File(filePath + fileName);
    //         try {
    //             file.transferTo(dest);
    //             LOGGER.info("第" + (i + 1) + "个文件上传成功");
    //         } catch (IOException e) {
    //             LOGGER.error(e.toString(), e);
    //             return "上传第" + (i++) + "个文件失败";
    //         }
    //     }

    //     return "上传成功";

    // }
    
    
    
    @PostMapping("/download")
    public String Download(HttpServletResponse response, @RequestParam String fileURL) {
        return fileService.Download(response, fileURL);
    }
    

    @RequestMapping("/findfile")
    private String FileFile(@RequestParam (value ="dir", defaultValue="gltf")String dir,  @RequestParam(value = "end",required = false) String[] end) {
    
            List<filentity> listOffilentity= new ArrayList<filentity>();
            if(!end[0].equals(".all")){
                listOffilentity = fileService.findAll(fpath+ dir ,  listOffilentity , end);
            }else{
                listOffilentity = fileService.findFilebyPath(dir.replace(furl, fpath));
            }
            JsonObject j = new JsonObject();
    
            return j.toJSONString(listOffilentity);
        
        
    }



    
    
    

}