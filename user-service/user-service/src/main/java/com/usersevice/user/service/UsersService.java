package com.usersevice.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.usersevice.user.dto.UserCreateDto;
import com.usersevice.user.entity.User;



public interface UsersService {
	Optional<User> findById(Integer userId);
	User findByUsername(String username);
	boolean createUser(UserCreateDto user);
	boolean updateUser(Integer userId, UserCreateDto user);
	List<User> findByRole();
	boolean chancePassword(Integer userId, String password);
	
	public void deleteUserById(Integer id);
}

