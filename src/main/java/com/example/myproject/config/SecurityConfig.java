package com.example.myproject.config;

import com.example.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final PasswordEncoder passwordEncoder;
    private final BlacklistAuthenticationFilter blacklistAuthenticationFilter;

    @Autowired
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
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/auth/login")
                .permitAll()
                .and()
                .addFilterBefore(blacklistAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}