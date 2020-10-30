package com.wenxin.learn.faststart.web.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/29 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer adminId;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件名
     */
    private String name;
}
