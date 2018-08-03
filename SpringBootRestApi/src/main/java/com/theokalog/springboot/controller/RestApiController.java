package com.theokalog.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.theokalog.springboot.model.User;
import com.theokalog.springboot.service.UserService;
import com.theokalog.springboot.service.UserServiceImpl;
import com.theokalog.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	@Autowired
	UserService userService; // Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All
	// Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		System.out.println("getting all users");
		List<User> users = userService.findAllUsers();

		if (users == null || users.isEmpty()) {
			System.out.println("no users found");
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// User------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {

		System.out.print("getting user with id: " + id);
		User user = userService.findById(id);

		if (user == null) {
			System.out.println("user with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		System.out.println("creating new user: " + user);

		if (userService.isUserExist(user)) {
			System.out.println("a user with name " + user.getName() + " already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}

	// ------------------- Update a User
	// ------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		System.out.println("updating user: " + user);
		User currentUser = userService.findById(id);

		if (currentUser == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		currentUser.setId(user.getId());
		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setSalary(user.getSalary());

		userService.updateUser(user);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);

	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		System.out.println("deleting user with id: " + id);
		User user = userService.findById(id);

		if (user == null) {
			System.out.println("Unable to delete. User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.OK);

	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		System.out.println("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);

	}

}