package com.fit.repository;

import org.springframework.data.repository.CrudRepository;

import com.fit.entity.User;

public interface UserRepository extends CrudRepository<User, String>{
	
	
}
