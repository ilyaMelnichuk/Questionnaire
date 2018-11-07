package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRoleName(String roleName);
}
