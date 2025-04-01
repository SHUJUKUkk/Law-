package com.example.service;

import com.example.entity.User;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findNormalUsers() {
        return userRepository.findByBlacklistedFalse();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("用户未找到"));
    }

    @Override
    public List<User> findBlacklistedUsers() {
        return userRepository.findByBlacklistedTrue();
    }

    @Override
    public void addToBlacklist(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("用户未找到"));
        if (user.isBlacklisted()) {
            throw new IllegalStateException("该用户已在黑名单中");
        }
        user.setBlacklisted(true);
        userRepository.save(user);
    }

    @Override
    public void removeFromBlacklist(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("用户未找到"));
        if (!user.isBlacklisted()) {
            throw new IllegalStateException("该用户不在黑名单中");
        }
        user.setBlacklisted(false);
        userRepository.save(user);
    }
}