package com.example.questionnaire.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.questionnaire.entity.PollTemplate;

public interface PollTemplateRepository extends PagingAndSortingRepository<PollTemplate, Long>{

}
