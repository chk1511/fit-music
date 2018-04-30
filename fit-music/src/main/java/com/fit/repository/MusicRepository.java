package com.fit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.fit.entity.Music;

public interface MusicRepository extends CrudRepository<Music, Integer>{

	Music updateOne(int id, Music music);

	Page<Music> findByPage(Pageable ageable);
	
	List<Music> findByPreference(boolean preference);
}
