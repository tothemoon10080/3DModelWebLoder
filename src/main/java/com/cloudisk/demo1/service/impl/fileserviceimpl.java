package com.cloudisk.demo1.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.cloudisk.demo1.entity.filentity;
import com.cloudisk.demo1.service.FileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




@Service
public class fileserviceimpl implements FileService{
    
    @Value("${datasource.file_save_path}")
    private String fpath;

    @Value("${datasource.file_http_url}")
    private String furl ;

    // String furl ="http://192.168.2.153:9018/";
    // String fpath="D:/model/"; 
        
    
   

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getOriginalFilename(String fileUrl) {
        File file =new File(UrltoPath(fileUrl));
        // TODO Auto-generated method stub
        return file.getName();
    }

    @Override
    public void transferTo() {
        // TODO Auto-generated method stub
        
    }

    
    
    
    @Override
    public List<filentity> findAll(String URL , List<filentity> listOffilentity , String[] end){//递归所有文件夹

    FileFilter fileFilter = new FileFilter() {

        @Override
        public boolean accept(File file) {
            // TODO Auto-generated method stub
            
            for (int i = 0; i < end.length;) {
                if(!file.isDirectory()){
                    if(file.getName().endsWith(end[i])){
                        return true;
                    }else{
                        i++;
                    }
                }else{
                    return true;
                }
            }
            return false;
        }
        
    };
    File file = new File(URL);
                   
    File[] f =file.listFiles(fileFilter);
        for (int i = 0; i < f.length; i++) {
            if (f[i].isFile()) {
                filentity filentity = new filentity();
                filentity.setFilDate(f[i].lastModified());
                filentity.setFileName(f[i].getName());
                filentity.setFileSize(f[i].length());
                
                filentity.setFileUrl(PathtoUrl(f[i].getPath()));
                
                listOffilentity.add(filentity);
                   
            }
            if (f[i].isDirectory()) {
                findAll( f[i].getPath() , listOffilentity,end);              
            }
    
                
        }
        return listOffilentity;
    }

    public List<filentity> findFilebyPath(String fpath){//不递归文件夹 
        File file = new File(fpath);
        File[] f =file.listFiles();
        List<filentity> listOffilentity = new ArrayList<filentity>();
        for (int i = 0; i < f.length; i++) {
            filentity filentity = new filentity();
            if(f[i].isFile()){
                filentity.setFileSize(f[i].length());
            }else{
                long zero = 0 ; 
                filentity.setFileSize(zero);
            }
            
            
            filentity.setFilDate(f[i].lastModified());
            filentity.setFileName(f[i].getName());
            filentity.setFileUrl(PathtoUrl(f[i].getPath()));
            listOffilentity.add(filentity);       
        }
        return listOffilentity;
    }

    public void mkdirs(String filePath) throws Exception{
        File file = new File(filePath);
        File parent = file.getParentFile(); 
        if(parent.isDirectory()){
            if(file.mkdir()){
                
            }else{
                throw new Exception("fail to mkdirs");
            }
        }else{
            throw new Exception("file not dirs");
        }
    }




    //tozip 
    @Override
    public void toZip(String filePath) {
        
        File file = new File(filePath);
        ZipOutputStream zipOut = null;
        InputStream input = null ;
        File zipFile = new File(filePath+".zip");
        if(file.isFile()){zipFile = new File(filePath.substring(0,filePath.lastIndexOf("."))+".zip");}
            
            
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            int pathlen = file.getPath().length()+1;
            writeZip(file ,zipOut,pathlen);
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
                //关闭流
                if(input!=null){try{input.close();}catch(IOException e){e.printStackTrace();}}
                if(zipOut!=null){try{zipOut.close();}catch(IOException e){e.printStackTrace();}}
        }
        
    }
    @Override
    public void toUnZip(String filePath) throws Exception {
        // TODO Auto-generated method stub
        File file = new File(filePath);
        int n =filePath.lastIndexOf(".");
        if(filePath.substring(n).equals(".zip")){
            
           
            ZipInputStream zipIn = null;
            FileOutputStream fileOut = null ;
            ZipEntry zipEntry = null;
            String rootpath = file.getPath().substring(0,file.getPath().lastIndexOf("\\"));
            try{
                zipIn =  new ZipInputStream ( new BufferedInputStream( new FileInputStream(filePath)), Charset.forName("gbk")); 

                while(( zipEntry =  zipIn.getNextEntry()) !=  null){ 
                    file =  new File(rootpath , zipEntry.getName()); 
                    //System.out.println(file);/// 

                    if( zipEntry.isDirectory()){ 
                        file.mkdirs(); 
                    } 
                    else{ 
                        //如果指定文件的目录不存在,则创建之. 
                        File parent = file.getParentFile(); 
                        if(!parent.exists()){ 
                            parent.mkdirs(); 
                        } 

                        fileOut =  new FileOutputStream(file); 
                        int len = 0;
                        byte[] buf = new byte[32768];

                        while ((len = zipIn.read(buf)) != -1) {
                            
                            fileOut.write(buf , 0 ,len);
                        }
                        fileOut.close();
                    }
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                try {
                    zipIn.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }else{
            throw new Exception("NOT ZIP FILE"); 
        }


    } 

    //Use java.util.zip to zip
    private void writeZip(File file, ZipOutputStream zipOut , int pathlen ) throws Exception{

        if (file.isFile()) {
                
            zipOut.putNextEntry(new ZipEntry(file.getPath().substring(pathlen)));

            FileInputStream input = new FileInputStream(file.getPath());
            int len = 0;
            Long l = file.length();
            byte[] buf = new byte[l.intValue()];
            while ((len = input.read(buf)) != -1){
                zipOut.write(buf,0,len);
			}
        }
        if (file.isDirectory()) {
            File[] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                    writeZip( filelist[i], zipOut , pathlen);
            }              
        }  
    }



    @Override
    public String getFilePathnoName(String filePath) {
        return filePath.replace(getOriginalFilename(PathtoUrl(filePath)), "");
    }

    @Override
    public String UrltoPath(String fileUrl) {


        // TODO Auto-generated method stub
        return fileUrl.replace(furl, fpath);
    }

    @Override
    public String PathtoUrl(String filePath) {
        // TODO Auto-generated method stub
        String a  =filePath.replace("\\", "/");
        return a.replace(fpath, furl);
    }

    public String Download(HttpServletResponse response , String fileURL){
        //待下载文件名
       
        //设置为png格式的文件
        // response.setHeader("content-type","gld");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + getOriginalFilename(fileURL));

       
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(UrltoPath(fileURL))));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
            // model.addAttribute("result","下载失败");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //成功后返回成功信息
        // model.addAttribute("result","下载成功");
        return "Success";
    }
}
