package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.OptionId;

public interface OptionRepository extends JpaRepository<Option, OptionId>{
}
