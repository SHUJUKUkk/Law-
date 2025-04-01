package com.example.myproject.repository;

import com.example.myproject.entity.Legal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalRepository extends JpaRepository<Legal, Long> {
    // 添加自定义查询方法，例如按案件名称查询
    Legal findByLegalCase(String legalCase);
}