package com.fq.modules.was.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class WasBackstageConfig {

    /**
     * 配置mysql数据库
     */
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    /***
     * 地址
     */
    @Value("${spring.datasource.url}")
    private String url;
    /***
     * 登录名
     */
    @Value("${spring.datasource.userName}")
    private String userName;
    /***
     * 登陆密码
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 初始化数据库配置
     * @return
     */
    @Bean
    public DataSource primaryDataSource() {

        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);//用户名
        dataSource.setPassword(password);//密码

        return dataSource;
    }
}
