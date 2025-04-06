package com.example.myproject.service.impl;

import com.example.myproject.dto.PasswordResetDto;
import com.example.myproject.dto.RegistrationDto;
import com.example.myproject.entity.User;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.exception.DatabaseException;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User registerUser(RegistrationDto registrationDto) throws Exception {
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new Exception("用户名 " + registrationDto.getUsername() + " 已存在!");
        }
        if (isEmailBlacklisted(registrationDto.getEmail())) {
            throw new IllegalStateException("该邮箱已被列入黑名单，无法注册新账户");
        }
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setRoles("ROLE_USER");
        user.setBlacklisted(false);
        return userRepository.save(user);
    }

    @Override
    public void resetPassword(PasswordResetDto passwordResetDto) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(passwordResetDto.getEmail());
        if (!optionalUser.isPresent()) {
            throw new Exception("未找到对应的用户");
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findNormalUsers() {
        try {
            return userRepository.findByBlacklistedFalse();
        } catch (Exception e) {
            logger.error("查询正常用户列表时发生错误", e);
            throw new DatabaseException("查询用户列表失败");
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("用户未找到"));
        } catch (Exception e) {
            logger.error("查询用户信息时发生错误，用户名: {}", username, e);
            throw new DatabaseException("查询用户信息失败");
        }
    }

    @Override
    public List<User> findBlacklistedUsers() {
        try {
            return userRepository.findByBlacklistedTrue();
        } catch (Exception e) {
            logger.error("查询黑名单用户列表时发生错误", e);
            throw new DatabaseException("查询用户列表失败");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void addToBlacklist(Long userId) throws Exception {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("用户不存在，ID: " + userId));
            if (user.isBlacklisted()) {
                throw new IllegalStateException("该用户已在黑名单中");
            }
            user.setBlacklisted(true);
            userRepository.save(user);
        } catch (UserNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            logger.error("将用户加入黑名单时发生错误，用户ID: {}", userId, e);
            throw new DatabaseException("将用户加入黑名单时发生错误");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void removeFromBlacklist(Long userId) throws Exception {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("用户不存在，ID: " + userId));
            if (!user.isBlacklisted()) {
                throw new IllegalStateException("该用户不在黑名单中");
            }
            user.setBlacklisted(false);
            userRepository.save(user);
        } catch (UserNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            logger.error("将用户从黑名单移除时发生错误，用户ID: {}", userId, e);
            throw new DatabaseException("将用户从黑名单移除时发生错误");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                !user.isBlacklisted(),
                true,
                true,
                true,
                Collections.singletonList(() -> user.getRoles())
        );
    }

    @Override
    public boolean isEmailBlacklisted(String email) {
        return userRepository.existsByEmailAndBlacklistedTrue(email);
    }
}
