package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questionnaire.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    public PasswordResetToken findByToken(String token);
}
