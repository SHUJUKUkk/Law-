
package com.example.myproject.service;

import com.example.myproject.entity.User;
import com.example.myproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始执行数据初始化...");
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setRoles("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("默认管理员账户已创建，用户名: admin, 密码: admin123");
        } else {
            System.out.println("默认管理员账户已存在");
        }
        System.out.println("管理员账户检查完成");
        System.out.println("数据初始化流程执行结束");
    }
}