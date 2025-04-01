package com.example.myproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.*;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        boolean isAdmin = false;
        // 输出调试信息，查看获取到的权限信息
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            logger.debug("用户权限：{}", authority.getAuthority());
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (isAdmin) {
            logger.debug("检测到管理员权限，重定向到 /admin/home");
            response.sendRedirect("/admin/home");
        } else {
            logger.debug("非管理员登录，重定向到 /user/home");
            response.sendRedirect("/user/home");
        }
    }
}