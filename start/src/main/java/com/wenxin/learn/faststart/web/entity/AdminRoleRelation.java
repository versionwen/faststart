package com.wenxin.learn.faststart.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/29 17:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRoleRelation implements Serializable {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long adminId;

    private Long roleId;
}
