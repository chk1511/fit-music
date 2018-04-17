package com.fit.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="users")
public class User {

	@Id
	private String id;
	private String password;
	private String name;
	
	@Field(value="preference_tf")
	private Boolean preferenceTf;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getPreferenceTf() {
		return preferenceTf;
	}
	public void setPreferenceTf(Boolean preferenceTf) {
		this.preferenceTf = preferenceTf;
	}
}