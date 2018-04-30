package com.fit.repository;

import org.springframework.data.repository.CrudRepository;

import com.fit.entity.Preference;

public interface PreferenceRepository extends CrudRepository<Preference, Integer> {

	
}
