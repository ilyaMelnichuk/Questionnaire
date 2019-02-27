package com.example.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.PollTemplateRepository;
import com.example.questionnaire.entity.PollTemplate;

@Service
public class PollTemplateService {
	@Autowired
	private PollTemplateRepository pollTemplateRepository;

	public Page<PollTemplate> findAll(PageRequest of) {
		return pollTemplateRepository.findAll(of);
	}
}
