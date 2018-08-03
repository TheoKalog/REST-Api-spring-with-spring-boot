package com.theokalog.springboot.repository;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.theokalog.springboot.model.User;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;


	public User getUserById(long id) {
		User user = null;

		String sql = "SELECT * FROM user WHERE ID=?";
		try {
			user = (User) jdbcTemplate.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper(User.class));
		} catch (DataAccessException e) {

		}
		return user;
	}

	public User getUserByName(String name) {
		User user = null;
		String sql = "SELECT * FROM user WHERE name=?";
		try {
			user = (User) jdbcTemplate.queryForObject(sql, new Object[] { name },
					new BeanPropertyRowMapper(User.class));
		} catch (DataAccessException e) {

		}
		return user;

	}

	public int saveUser(User user) {
		int a = 0;
		String sql = "insert into user (id, name, age,salary) values (?, ?, ?, ?)";
		try {
			a = jdbcTemplate.update(sql,
					new Object[] { user.getId(), user.getName(), user.getAge(), user.getSalary() });
		} catch (DataAccessException e) {

		}
		return a;
	}

	public int updateUser(User user) {
		int a = 0;
		String sql = "update user set name = ?, age = ? ,salary= ? where id = ?";
		try {
			a = jdbcTemplate.update(sql,
					new Object[] { user.getId(), user.getName(), user.getAge(), user.getSalary() });
		} catch (DataAccessException e) {

		}
		return a;
	}

	public int deleteUserById(long id) {
		return jdbcTemplate.update("delete from user where id=?", new Object[] { id });
	}

	public List<User> getAllUsers() {
		String sql = "SELECT * FROM user";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));

		return users;
	}

	public int deleteAllUsers() {
		String sql = "delete from user";

		return jdbcTemplate.update("delete from user", new Object[] {});
	}

}
