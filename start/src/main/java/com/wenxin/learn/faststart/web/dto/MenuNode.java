package com.wenxin.learn.faststart.web.dto;

import com.wenxin.learn.faststart.web.entity.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/13 17:54
 */
@Getter
@Setter
public class MenuNode extends Menu {
    @ApiModelProperty(value = "子菜单")
    private List<MenuNode>children;
}
