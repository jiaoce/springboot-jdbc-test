package com.jiaoce.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    //    后台监控
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean<StatViewServlet> statViewServletServletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
//          后台登录，账号密码配置
        HashMap<String, String> configInit = new HashMap<>();
        configInit.put("loginUsername", "admin");
        configInit.put("loginPassword", "123456");

//        允许谁能访问: “” ->都可以访问
        configInit.put("allow", "");

//        禁止谁能访问
//        configInit.put("jiaoce","192.168.1.20");

        statViewServletServletRegistrationBean.setInitParameters(configInit);
        return statViewServletServletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean webStartFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new WebStatFilter());

        HashMap<String, String> initConfig = new HashMap<>();
        initConfig.put("exclusions", "*.js,*.css,/druid/*");


        filterFilterRegistrationBean.setInitParameters(initConfig);
        return filterFilterRegistrationBean;
    }
}
