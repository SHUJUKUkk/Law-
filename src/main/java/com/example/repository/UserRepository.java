package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象，可能为null
     */
    Optional<User> findByUsername(String username);

    /**
     * 查找所有在黑名单中的用户
     * @return 黑名单用户列表
     */
    List<User> findByBlacklistedTrue();

    /**
     * 查找所有不在黑名单中的用户
     * @return 非黑名单用户列表
     */
    List<User> findByBlacklistedFalse();
}