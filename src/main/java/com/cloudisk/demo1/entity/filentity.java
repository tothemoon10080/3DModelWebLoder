package com.cloudisk.demo1.entity;



import com.alibaba.fastjson.annotation.JSONField;

public class filentity {

    @JSONField(name="FILENAME")
    private String fileName;

    @JSONField(name = "FILEURL")
    private String fileUrl;

    @JSONField(name = "FILESIZE")
    private long fileSize;

    @JSONField(name = "FILEDATE")
    private long filDate;
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void setFilDate(Long filDate) {
        this.filDate = filDate;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    

    public String getFileName() {
        return fileName;
    }
    public String getFileUrl() {
        return fileUrl;
    }
    public long getFilDate() {
        return filDate;
    }
    public long getFileSize() {
        return fileSize;
    }
}

