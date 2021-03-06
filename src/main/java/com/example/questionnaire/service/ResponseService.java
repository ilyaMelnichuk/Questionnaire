package com.example.questionnaire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.ResponseRepository;
import com.example.questionnaire.dao.UserRepository;
import com.example.questionnaire.entity.Response;

@Service
public class ResponseService {
    @Autowired 
    ResponseRepository responseRepository;
    @Autowired 
    UserRepository userRepository;
    
    public long getMaximalId() {
    	return responseRepository.getMaximalId();
    }
    public void saveResponse(Response response) {
    	responseRepository.save(response);
    }
	public Page<Response> findAll(PageRequest pageRequest) {
		return responseRepository.findAll(pageRequest);
	}
	public List<Response> findAllResponses() {
		return responseRepository.findAll();
	}
	public List<Response> findByEmail(String email) {
		return responseRepository.findByUser(userRepository.findByEmail(email));
	}
}
