package com.example.questionnaire.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Type;

@Repository
public interface FieldRepository extends JpaRepository<Field, String>{
	@Transactional
    @Modifying
    @Query("update Field f set f.label = ?2, f.type = ?3, f.required = ?4, f.isActive = ?5 where f.label = ?1")
    void updateByLabel(String oldLabel, String newLabel, Type newType, boolean newRequired, boolean newActive);
	
	Iterable<Field> findByIsActive(boolean isActive);
	boolean existsByLabel(String label);
}
