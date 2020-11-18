package com.wenxin.learn.faststart.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wenxin.learn.faststart.web.entity.Image;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 图片 服务类
 * </p>
 *
 * @author version
 * @since 2020-10-29
 */
public interface ImageService extends IService<Image> {
    boolean fileUpLoad(MultipartFile file,Integer adminId);

    /**
     *
     * @param adminId 管理员ID
     * @param pageSize 页面大小
     * @param pageNum 每一页数量
     * @return
     */
    Page<Image> fileDownLoadList(Integer adminId,Integer pageSize,Integer pageNum);

}
