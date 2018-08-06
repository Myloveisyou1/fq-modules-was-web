package com.fq.modules.was.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//启动定时任务
@EnableFeignClients(basePackages = {"com.fq.modules.client", "com.fq.modules.was.web.client"})//配置要扫描的feign
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.fq.modules.was.web", "com.fq.modules.client"})//配置要扫描的包,避免初始化的时候无法找到不是本项目的文件
@MapperScan(basePackages = {"com.fq.modules.was.web.mapper"})
public class WasWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WasWebApplication.class, args);
    }
}
