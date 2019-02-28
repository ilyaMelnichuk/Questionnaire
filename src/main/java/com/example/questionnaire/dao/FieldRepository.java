package com.example.questionnaire.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Type;

@Repository
public interface FieldRepository extends PagingAndSortingRepository<Field, String>{
	
	
	/*@Transactional
	  
	@Modifying
	  
    @Query("update Field f set f.label = :newLabel, f.type = :newType, f.required = :newRequired, f.isActive = :newIsActive where f.id = :id") 
	void updateById(@Param("id") Long id, @Param("newLabel") String label, @Param("newType") Type newType, @Param("newRequired") boolean newRequired, @Param("newIsActive") boolean newActive);
	 */
	Iterable<Field> findByIsActive(boolean isActive);
	
	Field findById(Long id);
	
	@Modifying
	void deleteById(Long id);
	boolean existsById(String id);

	Page<Field> findByPollTemplate_idNot(PageRequest of, Long id);

	Page<Field> findByPollTemplate_id(PageRequest of, Long id);
}
