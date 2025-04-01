package com.example.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 修改后的映射路径为 "/home"
    @GetMapping("/home")
    public String index() {
        return "index";  // 返回模板 pages，例如 templates/index.html
    }
}