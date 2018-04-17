package com.fit.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="preferences")
public class Preference {
	
	@Id
	private String id;
	
	@Field(value="music_id")
	private String musicId;

	@Field(value="user_id")
	private String userId;
	
	@Field(value="score")
	private String score;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMusicId() {
		return musicId;
	}

	public void setMusicId(String musicId) {
		this.musicId = musicId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
}