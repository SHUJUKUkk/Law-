package com.example.service;

import com.example.entity.User;
import java.util.List;

public interface UserService {
    List<User> findNormalUsers();
    User findByUsername(String username);
    List<User> findBlacklistedUsers();
    void addToBlacklist(Long userId);
    void removeFromBlacklist(Long userId);
}