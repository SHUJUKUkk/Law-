package com.example.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // 定义密码编码器 Bean，用于安全相关的密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 当访问根路径 "/" 时，通过 Thymeleaf 渲染 index 模板
    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("dataContent", "这里是动态数据内容");
        return "index"; // 此处会渲染 src/main/resources/templates/index.html
    }
}