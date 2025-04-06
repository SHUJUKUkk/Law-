package com.example.myproject.repository;

import com.example.myproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findUsersByUsernameContainingOrEmailContaining(String username, String email);

    List<User> findByBlacklistedTrue();

    List<User> findByBlacklistedFalse();

    boolean existsByEmailAndBlacklistedTrue(String email);
}
