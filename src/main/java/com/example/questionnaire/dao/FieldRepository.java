package com.example.questionnaire.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Type;

@Repository
public interface FieldRepository extends PagingAndSortingRepository<Field, String>{
	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query("update Field f set f.label = ?2, f.type = ?3, f.required = ?4, f.isActive = ?5 where f.label = ?1"
	 * ) void updateByLabel(String oldLabel, String newLabel, Type newType, boolean
	 * newRequired, boolean newActive);
	 */

	
	
	@Transactional
	  
	@Modifying
	  
    @Query("update Field f set f.label = ?2, f.type = ?3, f.required = ?4, f.isActive = ?5 where f.id = ?1") 
	void updateByI(Long id, String Label, Type newType, boolean newRequired, boolean newActive);
	 
	Iterable<Field> findByIsActive(boolean isActive);
	//Field findByLabel(String label);
	Field findById(Long id);
	Field deleteById(Long id);;
	boolean existsById(String id);
}
