package com.cloudisk.demo1.service.impl;

public class test {
    public static void main(String[] args) throws Exception {
        fileserviceimpl fileserviceimpl = new fileserviceimpl();
        String filePath = "D:/model/mmd/babala.zip";
        
        fileserviceimpl.toUnZip(filePath);
    }
}
