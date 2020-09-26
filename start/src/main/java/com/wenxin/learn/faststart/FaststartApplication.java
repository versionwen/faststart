package com.wenxin.learn.faststart;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger2Doc
public class FaststartApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaststartApplication.class, args);
    }

}
