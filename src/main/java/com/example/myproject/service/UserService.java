package com.example.myproject.service;

import com.example.myproject.dto.PasswordResetDto;
import com.example.myproject.dto.RegistrationDto;
import com.example.myproject.entity.User;
import com.example.myproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegistrationDto registrationDto) throws Exception {
        if(userRepository.findByUsername(registrationDto.getUsername()).isPresent()){
            throw new Exception("用户名 " + registrationDto.getUsername() + " 已存在!");
        }
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        // 默认角色为普通用户
        user.setRoles("ROLE_USER");
        return userRepository.save(user);
    }

    public void resetPassword(PasswordResetDto passwordResetDto) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(passwordResetDto.getEmail());
        if (!optionalUser.isPresent()){
            throw new Exception("未找到对应的用户");
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(() -> user.getRoles())
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void addToBlacklist(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new Exception("用户不存在");
        }
        User user = optionalUser.get();
        user.setBlacklisted(true);
        userRepository.save(user);
    }

    public void removeFromBlacklist(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new Exception("用户不存在");
        }
        User user = optionalUser.get();
        user.setBlacklisted(false);
        userRepository.save(user);
    }

    public List<User> getBlacklistedUsers() {
        return userRepository.findByBlacklistedTrue();
    }
}