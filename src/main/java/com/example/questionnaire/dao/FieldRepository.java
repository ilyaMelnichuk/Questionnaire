package com.example.questionnaire.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.model.Field;

@Repository
public interface FieldRepository extends CrudRepository<Field, Long>{
	@Query(value = "SELECT * FROM SCHEMA.FIELDS", nativeQuery = true)
	    public List<Field> findAllFields(String s);
}
