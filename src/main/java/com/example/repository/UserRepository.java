package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 查找所有非黑名单用户
     * @return 非黑名单用户列表
     */
    List<User> findByBlacklistedFalse();
    
    /**
     * 查找所有黑名单用户
     * @return 黑名单用户列表
     */
    List<User> findByBlacklistedTrue();
}