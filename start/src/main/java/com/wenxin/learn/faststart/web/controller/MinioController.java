package com.wenxin.learn.faststart.web.controller;

import io.minio.MinioClient;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author version
 * @version 1.0
 * @date 2020/9/1 15:03
 */
@RestController
@RequestMapping("/minio")
public class MinioController {
    private static String url = "http://127.0.0.1:9000";  //minio服务的IP端口
    private static String accessKey = "minioadmin";
    private static String secretKey = "minioadmin";

    //上传文件到minio服务
    @PostMapping("upload")
    public ResponseEntity upload(@RequestParam("fileName") MultipartFile file )  {
        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            boolean isExist = minioClient.bucketExists("test");
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket("test");
            }
            InputStream is= file.getInputStream(); //得到文件流
            String fileName = file.getOriginalFilename(); //文件名
            String contentType = file.getContentType();  //类型
            System.out.println("is="+is+"fileName="+fileName+"contentType="+contentType);
            minioClient.putObject("test",fileName,is,contentType); //把文件放置Minio桶(文件夹)
            String fileUrl = minioClient.presignedGetObject("test", fileName);
            return  new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }
    //下载minio服务的文件
    @GetMapping("download")
    public ResponseEntity download(HttpServletResponse response){
        try {
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            InputStream fileInputStream = minioClient.getObject("test", "test.jpg");
            response.setHeader("Content-Disposition", "attachment;filename=" + "test.jpg");
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream,response.getOutputStream());
            return  new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }
    //获取minio文件的下载地址
    @GetMapping("url")
    public  String  getUrl(@RequestParam("filename") String filename){
        try {
            System.out.println("filename="+filename);
            MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
            String url = minioClient.presignedGetObject("test", filename);
            return url;
        }catch (Exception e){
            return "获取失败";
        }
    }
}
