package com.theokalog.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.theokalog.springboot.model.User;
import com.theokalog.springboot.repository.UserRepository;



@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User findById(long id) {
		return userRepository.getUserById(id);
	}

	@Override
	public User findByName(String name) {
		return userRepository.getUserByName(name);
	}

	@Override
	public void saveUser(User user) {
		userRepository.saveUser(user);
		
	}

	@Override
	public void updateUser(User user) {
		userRepository.updateUser(user);

	}

	@Override
	public void deleteUserById(long id) {
		userRepository.deleteUserById(id);

	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.getAllUsers();

	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAllUsers();
		
	}

	@Override
	public boolean isUserExist(User user) {
		return findByName(user.getName()) != null;
	}



	
}
