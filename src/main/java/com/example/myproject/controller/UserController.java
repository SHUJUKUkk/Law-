package com.example.myproject.controller;

import com.example.myproject.entity.Legal;
import com.example.myproject.service.LegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LegalService legalService;

    // 用户主页
    @GetMapping("/home")
    public String userHome(Model model) {
        model.addAttribute("legals", legalService.getAllLegals());
        return "user/home";
    }

    // 浏览所有法律条文
    @GetMapping("/legal")
    public String viewAllLegals(Model model) {
        model.addAttribute("legals", legalService.getAllLegals());
        return "user/legal_list";
    }

    // 根据案件名称浏览法律条文
    @GetMapping("/legal/{caseName}")
    public String viewLegalByCase(@PathVariable String caseName, Model model) {
        Legal legal = legalService.getLegalByCase(caseName);
        model.addAttribute("legal", legal);
        return "user/legal_detail";
    }
}