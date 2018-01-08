package com.github.jamsa.rap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by zhujie on 2018/1/8.
 */

@SpringBootApplication
//@MapperScan("com.github.jamsa.rap.mapper")
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
