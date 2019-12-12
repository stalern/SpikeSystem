package com.savannah;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot启动类
 * @author stalern
 * @date 2019/12/09~15:48
 */
@MapperScan("com.savannah.dao")
@SpringBootApplication(scanBasePackages = {"com.savannah"})
public class SpikeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpikeApplication.class, args);
    }
}
