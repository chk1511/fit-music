package com.fit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.fit.entity.Music;
import com.fit.entity.User;

public interface MusicRepository extends CrudRepository<Music, Integer>{

	Music updateOne(int id, enti)

	Page<Music> findByPage(Pageable ageable);
}
