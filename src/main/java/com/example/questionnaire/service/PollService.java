package com.example.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.PollRepository;
import com.example.questionnaire.dao.UserRepository;
import com.example.questionnaire.entity.Poll;

@Service
public class PollService {
	@Autowired
	PollRepository pollRepository;
	@Autowired
	UserRepository userRepository;

	public Poll savePoll(Poll poll) {
		return pollRepository.save(poll);
	}
	public Page<Poll> findAll(PageRequest pageRequest){
		return pollRepository.findAll(pageRequest);
	}
	public Page<Poll> findByEmail(String email, PageRequest pageRequest){
		return pollRepository.findByUser_Email(email, pageRequest);
	}
}
