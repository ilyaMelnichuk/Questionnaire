package com.example.questionnaire.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.model.User;

@Repository
public interface UserRepository  extends CrudRepository<User, Long>{
	@Query(value = "SELECT * FROM SECURITY.USERS WHERE EMAIL = ?1", nativeQuery = true)
    User findByEmail(String email);
	@Query(value = "INSERT INTO SECURITY.USERS (EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER) VALUES(?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	void createUser(String email, String password, String firstName, String lastName, String phoneNumber);
	@Query(value = "UPDATE SECURITY.USERS SET FIRST_NAME = ?2, LAST_NAME = ?3, PHONE_NUMBER = ?4 WHERE EMAIL = ?1", nativeQuery = true)
	void editUser(String email, String firstName, String lastName, String phoneNumber);
	@Query(value = "UPDATE SECURITY.USERS SET PASSWORD = ?2 WHERE EMAIL = ?1", nativeQuery = true)
	void changeUsersPassword(String email, String password);
}
