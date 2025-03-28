package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final PasswordEncoder passwordEncoder;
    private final BlacklistAuthenticationFilter blacklistAuthenticationFilter;

    public SecurityConfig(UserService userService,
                         CustomAuthenticationSuccessHandler successHandler,
                         PasswordEncoder passwordEncoder,
                         BlacklistAuthenticationFilter blacklistAuthenticationFilter) {
        this.userService = userService;
        this.successHandler = successHandler;
        this.passwordEncoder = passwordEncoder;
        this.blacklistAuthenticationFilter = blacklistAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // 禁用 CSRF（测试阶段使用）
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(successHandler)
                .failureUrl("/auth/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/auth/login")
                .permitAll()
                .and()
                .addFilterBefore(blacklistAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}