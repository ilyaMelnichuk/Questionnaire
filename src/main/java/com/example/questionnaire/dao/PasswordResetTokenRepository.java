package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questionnaire.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    public PasswordResetToken findByToken(String token);
}
