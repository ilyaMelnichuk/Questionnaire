package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.questionnaire.entity.Response;
import com.example.questionnaire.entity.ResponseId;

public interface ResponseRepository extends JpaRepository<Response, ResponseId>{
	@Query("select coalesce(max(r.id) + 1, 0) FROM Response r")
    public long getMaximalId();
}
