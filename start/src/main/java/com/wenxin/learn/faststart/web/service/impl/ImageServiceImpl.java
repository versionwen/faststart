package com.wenxin.learn.faststart.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.dto.ImageResult;
import com.wenxin.learn.faststart.web.entity.Image;
import com.wenxin.learn.faststart.web.mapper.ImageMapper;
import com.wenxin.learn.faststart.web.service.ImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 用户上传的图片 服务实现类
 * </p>
 *
 * @author version
 * @since 2020-10-29
 */
@Service
@Slf4j
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {
    @Value("${image.path}")
    private String imagePath;
    @Value("${image.virtualpath}")
    private String virtualpath;
    @Autowired
    private ImageMapper imageMapper;
    @Override
    public boolean fileUpLoad(MultipartFile file,Integer adminId) {
        String name = file.getOriginalFilename();
        String suffixName = name.substring(name.lastIndexOf(".")+1);
        String filePath = imagePath;
        String fileName = UUID.randomUUID()+"."+suffixName;
        File dest = new File(filePath+fileName);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            Image image = new Image();
            image.setCreatTime(new Date());
            image.setName(name);
            image.setAdminId(adminId);
            image.setFileName(fileName);
            image.setFileType(suffixName);
            image.setPath(virtualpath+"/"+fileName);
            imageMapper.insert(image);
            return true;
        } catch (IOException e) {
            log.info("存储文件出现异常：{}",e);
            return false;
        }
    }

    @Override
    public Page<Image> fileDownLoadList(Integer adminId, Integer pageSize, Integer pageNum) {
        Page<Image> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Image>wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Image::getAdminId,adminId).orderByDesc(Image::getCreatTime);
        return page(page,wrapper);
    }


}
