package com.example.questionnaire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.PollRepository;
import com.example.questionnaire.entity.Poll;
import com.example.questionnaire.entity.User;

@Service
public class PollService {
    @Autowired
    PollRepository pollRepository;
    
    public List<Poll> findAllPolls(){
    	return pollRepository.findAll();
    }
    public List<Poll> findPollsByEmail(String email){
    	return pollRepository.findAllByUser_Email(email);
    }
    public void savePoll(Poll poll) {
    	pollRepository.save(poll);
    }
}
