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
    @Query(value = "INSERT INTO SCHEMA.FIELDS (ID, LABEL, TYPE, REQUIRED, ISACTIVE) VALUES(?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
        public void insertField(long id, String label, String type, boolean required, boolean isactive);
    @Query(value = "DELETE FROM SCHEMA.FIELDS WHERE ID = ?1", nativeQuery = true)
        public void deleteField(long id);
    @Query(value = "UPDATE SCHEMA.FIELDS SET LABEL = ?2, TYPE = ?3, REQUIRED = ?4, ISACTIVE = ?5 WHERE ID = ?1", nativeQuery = true)
        public void updateField(long id, String label, String type, boolean required, boolean isactive);
    
}
