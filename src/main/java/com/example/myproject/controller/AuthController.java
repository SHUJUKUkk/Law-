package com.example.myproject.controller;

import com.example.myproject.dto.RegistrationDto;
import com.example.myproject.dto.PasswordResetDto;
import com.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    // 显示登录页面（实际登录由 Spring Security 处理）
    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    // 显示注册页面
    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("registration", new RegistrationDto());
        return "auth/register";
    }

    // 处理注册请求
    @PostMapping("/register")
    public String register(@ModelAttribute("registration") RegistrationDto registrationDto, Model model){
        try {
            userService.registerUser(registrationDto);
            return "redirect:/auth/login";
        } catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    // 显示忘记密码页面
    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model){
        model.addAttribute("passwordReset", new PasswordResetDto());
        return "auth/forgot_password";
    }

    // 处理密码重置请求
    @PostMapping("/forgot-password")
    public String resetPassword(@ModelAttribute("passwordReset") PasswordResetDto passwordResetDto, Model model){
        try {
            userService.resetPassword(passwordResetDto);
            return "redirect:/auth/login";
        } catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "auth/forgot_password";
        }
    }

    // 处理登录失败
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", "用户名或密码错误");
        return "auth/login-error";
    }
}