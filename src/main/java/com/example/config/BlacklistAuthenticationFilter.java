package com.example.config;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BlacklistAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    public BlacklistAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            
            if (user != null && user.isBlacklisted()) {
                SecurityContextHolder.clearContext();
                response.sendRedirect("/auth/login?error=blacklisted");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}