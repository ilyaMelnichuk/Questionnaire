package com.example.questionnaire.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.questionnaire.entity.Response;
import com.example.questionnaire.entity.ResponseId;
import com.example.questionnaire.entity.User;

public interface ResponseRepository extends JpaRepository<Response, ResponseId>{
	@Query("SELECT coalesce(max(r.id) + 1, 0) FROM Response r")
    public long getMaximalId();
}
