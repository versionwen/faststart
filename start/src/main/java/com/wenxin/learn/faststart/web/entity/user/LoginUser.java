package com.wenxin.learn.faststart.web.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author version
 * @version 1.0
 * @date 2020/9/5 10:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private String userName;
    private String passWord;
}
