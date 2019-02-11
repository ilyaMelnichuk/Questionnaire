package com.example.questionnaire.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.questionnaire.entity.Poll;
import com.example.questionnaire.entity.User;

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
    List<Poll> findAll();
    
    List<Poll> findAllByUser_Email(String email);
}
