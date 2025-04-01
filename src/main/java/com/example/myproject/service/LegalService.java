package com.example.myproject.service;

import com.example.myproject.entity.Legal;
import com.example.myproject.repository.LegalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalService {
    @Autowired
    private LegalRepository legalRepository;

    // 查询所有法律条文
    public List<Legal> getAllLegals() {
        return legalRepository.findAll();
    }

    // 根据案件名称查询法律条文
    public Legal getLegalByCase(String legalCase) {
        return legalRepository.findByLegalCase(legalCase);
    }

    // 添加新的法律条文
    public Legal addLegal(Legal legal) {
        return legalRepository.save(legal);
    }

    // 删除法律条文
    public void deleteLegal(Long id) {
        legalRepository.deleteById(id);
    }
}