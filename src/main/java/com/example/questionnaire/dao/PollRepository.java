package com.example.questionnaire.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.questionnaire.entity.Poll;

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
    List<Poll> findAll();
}
