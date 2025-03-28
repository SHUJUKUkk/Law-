package com.example.service;

import com.example.entity.User;
import java.util.List;

public interface UserService {
    List<User> findNormalUsers();
    List<User> findBlacklistedUsers();
    void addToBlacklist(Long userId);
    void removeFromBlacklist(Long userId);
    User findByUsername(String username);
}