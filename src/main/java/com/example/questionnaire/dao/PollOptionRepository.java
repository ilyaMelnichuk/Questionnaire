package com.example.questionnaire.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.OptionId;
import com.example.questionnaire.entity.PollField;
import com.example.questionnaire.entity.PollOption;
import com.example.questionnaire.entity.PollOptionId;

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, PollOptionId>{

	boolean existsByPollFieldId(Long id); 

	@Query("delete from PollOption p where p.pollField = :pollField") 
	void deleteByFieldId(@Param("pollField") PollField pollField);

	@Transactional
	@Modifying
	@Query("delete from PollOption p where p.id > ?1 and p.pollField = ?2")
	void deleteWhereIdMoreThan(Long id, PollField pollField);
}
