package com.example.questionnaire.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Field;
import com.example.questionnaire.entity.Option;
import com.example.questionnaire.entity.OptionId;

@Repository
public interface OptionRepository extends JpaRepository<Option, OptionId>{
	boolean existsByFieldLabel(String Label);
	@Modifying
    @Transactional
    @Query("delete from Option o where o.field = ?1")
    void deleteByFieldLabel(Field field);
	@Modifying
    @Transactional
    @Query("delete from Option o where o.id > ?1 and o.field = ?2")
    void deleteWhereIdMoreThan(long id, Field field);
}
