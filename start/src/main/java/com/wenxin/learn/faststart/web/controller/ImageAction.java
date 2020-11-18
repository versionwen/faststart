package com.wenxin.learn.faststart.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.api.CommonPage;
import com.wenxin.learn.faststart.web.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.wenxin.learn.faststart.web.service.ImageService;
import com.wenxin.learn.faststart.web.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户上传的图片 前端控制器
 * </p>
 *
 * @author version
 * @since 2020-10-29
 */
@Api(tags = "用户图片相关")
@RestController
@RequestMapping("/image")
public class ImageAction {
    @Autowired
    private ImageService imageService;
    @PostMapping(value = "/uploadfile")
    public CommonResult UpLoadFile(@RequestParam("file") MultipartFile file,@RequestParam("adminId") Integer adminId) {
        boolean success = imageService.fileUpLoad(file, adminId);
        if(success){
            return CommonResult.success("上传文件成功");
        }
        else {
            return CommonResult.failed("上传文件失败");
        }
    }
    @ApiOperation("分页查询用户上传的图片")
    @PostMapping("/listImage")
    public CommonResult DownLoadFile(@RequestParam("adminId") Integer adminId,@RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        Page<Image>imagePage = imageService.fileDownLoadList(adminId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(imagePage));
    }

}
