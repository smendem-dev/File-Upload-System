package com.app.file.upload.service;

import java.util.Optional;

import com.app.file.upload.entity.User;

public interface UserService {

	public User findByUsername(String username);
	public boolean validateUser(String username, String password);
	
}
