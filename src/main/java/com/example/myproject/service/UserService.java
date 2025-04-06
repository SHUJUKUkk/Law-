package com.example.myproject.service;

import com.example.myproject.dto.PasswordResetDto;
import com.example.myproject.dto.RegistrationDto;
import com.example.myproject.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void deleteUser(Long id);
    User registerUser(RegistrationDto registrationDto) throws Exception;
    void resetPassword(PasswordResetDto passwordResetDto) throws Exception;
    List<User> getAllUsers();
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    User updateUser(User user);
    List<User> findNormalUsers();
    User findByUsername(String username);
    List<User> findBlacklistedUsers();
    void addToBlacklist(Long userId) throws Exception;
    void removeFromBlacklist(Long userId) throws Exception;
    boolean isEmailBlacklisted(String email);
}
