package com.example.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.dao.ResponseRepository;
import com.example.questionnaire.entity.Response;

@Service
public class ResponseService {
    @Autowired 
    ResponseRepository responseRepository;
    
    public long getMaximalId() {
    	return responseRepository.getMaximalId();
    }
    public void saveResponse(Response response) {
    	responseRepository.save(response);
    }
}
