package com.example.myproject.controller;

import com.example.myproject.entity.Legal;
import com.example.myproject.service.LegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LegalService legalService;

    // 添加法律条文
    @PostMapping("/legal")
    public String addLegal(@ModelAttribute Legal legal, Model model) {
        legalService.addLegal(legal);
        model.addAttribute("message", "法律条文添加成功！");
        return "admin/legal_form";
    }

    // 删除法律条文
    @DeleteMapping("/legal/{id}")
    public String deleteLegal(@PathVariable Long id, Model model) {
        legalService.deleteLegal(id);
        model.addAttribute("message", "法律条文删除成功！");
        return "redirect:/admin/legal";
    }

    // 显示添加法律条文的表单
    @GetMapping("/legal/form")
    public String showAddLegalForm(Model model) {
        model.addAttribute("legal", new Legal());
        return "admin/legal_form";
    }

    // 显示法律条文管理页面
    @GetMapping("/legal")
    public String showLegalManagement(Model model) {
        model.addAttribute("legals", legalService.getAllLegals());
        return "admin/legal_management";
    }

    // 管理员主页
    @GetMapping("/home")
    public String adminHome() {
        return "admin/home";
    }
}