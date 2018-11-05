package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	@Query(value = "SELECT * FROM SECURITY.ROLES WHERE ROLE_NAME = ?1", nativeQuery = true)
    Role findByRoleName(String RoleName);
}
