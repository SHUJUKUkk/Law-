package com.example.service.impl;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.repository.UserRepository;
import com.example.exception.UserNotFoundException;
import com.example.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findNormalUsers() {
        try {
            return userRepository.findByBlacklistedFalse();
        } catch (Exception e) {
            logger.error("获取普通用户列表失败", e);
            throw new DatabaseException("获取用户列表时发生错误");
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findBlacklistedUsers() {
        try {
            return userRepository.findByBlacklistedTrue();
        } catch (Exception e) {
            logger.error("获取黑名单用户列表失败", e);
            throw new DatabaseException("获取黑名单用户列表时发生错误");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void addToBlacklist(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("用户不存在，ID: " + userId));
            if (user.isBlacklisted()) {
                throw new IllegalStateException("该用户已在黑名单中");
            }
            user.setBlacklisted(true);
            userRepository.save(user);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            logger.error("将用户加入黑名单时发生错误，用户ID: {}", userId, e);
            throw new DatabaseException("将用户加入黑名单时发生错误");
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void removeFromBlacklist(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("用户不存在，ID: " + userId));
            if (!user.isBlacklisted()) {
                throw new IllegalStateException("该用户不在黑名单中");
            }
            user.setBlacklisted(false);
            userRepository.save(user);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            logger.error("将用户从黑名单移除时发生错误，用户ID: {}", userId, e);
            throw new DatabaseException("将用户从黑名单移除时发生错误");
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElse(null);
        } catch (Exception e) {
            logger.error("查询用户信息时发生错误，用户名: {}", username, e);
            return null;
        }
    }
}