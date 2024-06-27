package com.app.file.upload.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.file.upload.entity.User;
import com.app.file.upload.exception.UserNotFoundException;
import com.app.file.upload.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userObj;

	@Override
	public User findByUsername(String username) {
		User user = userObj.findByUsername(username);
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}
		return user;
	}

	@Override
	public boolean validateUser(String username, String password) {
		User user = findByUsername(username);
		return user.getPassword().equals(password);
	}


}
