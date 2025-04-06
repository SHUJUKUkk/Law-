package com.example.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            // 可以根据需求定制错误信息，例如“用户名或密码错误！”
            model.addAttribute("errorMessage", "用户名或密码错误！");
        }
        return "login";
    }
}
