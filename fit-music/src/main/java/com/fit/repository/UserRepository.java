package com.fit.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fit.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
	
	@Modifying
	@Query("update users set preference = true where id = :id")
	User updatePreference(@Param("id") String id);
}
