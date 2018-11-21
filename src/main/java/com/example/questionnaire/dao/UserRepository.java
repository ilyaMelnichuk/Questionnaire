package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.questionnaire.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{
    User findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
    @Transactional
    @Modifying
    @Query("update User u set u.email = ?2, u.firstName = ?3, u.lastName = ?4, u.phoneNumber = ?5 where u.email = ?1")
    void updateByEmail(String oldEmail, String newEmail, String newFirstName, String newLastName, String newPhoneNumber);
}
