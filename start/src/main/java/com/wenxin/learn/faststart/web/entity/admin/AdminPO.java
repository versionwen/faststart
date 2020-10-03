package com.wenxin.learn.faststart.web.entity.admin;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author version
 * @version 1.0
 * @date 2020/10/3 11:07
 */
@Slf4j
@Data
public class AdminPO {
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String Email;
}
