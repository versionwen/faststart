package com.wenxin.learn.faststart.web.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
/**
 * <p>
 * 用户上传的图片
 * </p>
 *
 * @author version
 * @since 2020-10-29
 */
@TableName("doctor_image")
public class Image implements Serializable {

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creatTime;

    /**
     * 文件名
     */
    @JsonIgnore
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件存储路径
     * @return
     */
    private String path;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", creatTime=" + creatTime +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
