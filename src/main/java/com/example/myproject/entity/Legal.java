package com.example.myproject.entity;

import javax.persistence.*;

@Entity
@Table(name = "legal")
public class Legal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "legal_text", nullable = false, length = 10000)
    private String legalText;

    @Column(name = "legal_case", nullable = false, length = 1000)
    private String legalCase;

    @Column(name = "time", nullable = false)
    private String time;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLegalText() {
        return legalText;
    }

    public void setLegalText(String legalText) {
        this.legalText = legalText;
    }

    public String getLegalCase() {
        return legalCase;
    }

    public void setLegalCase(String legalCase) {
        this.legalCase = legalCase;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}