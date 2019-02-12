package com.example.questionnaire.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.questionnaire.entity.Poll;
import com.example.questionnaire.entity.User;

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {

	Page<Poll> findByUser_Email(String email, PageRequest pageRequest);	
}
