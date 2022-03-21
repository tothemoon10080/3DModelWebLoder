package com.cloudisk.demo1.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cloudisk.demo1.entity.filentity;


public interface FileService {
    
    boolean isEmpty();

    String UrltoPath(String fileUrl);

    String PathtoUrl(String filePath);
    
    void toZip(String filePath);

    void toUnZip(String filePath) throws Exception;

    void mkdirs(String filePath) throws Exception;

    String Download(HttpServletResponse response , String fileURL);

    String getOriginalFilename(String fileUrl);

    String getFilePathnoName(String filePath);

    void transferTo();

    List<filentity> findAll(String URL, List<filentity> listOffilentity,String[] end);

    List<filentity> findFilebyPath(String fpath);
}
