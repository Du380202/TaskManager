package com.web.webservice.api;

import java.util.List;

import com.web.webservice.dto.CreateUser;
import com.web.webservice.dto.UserDto;

public interface UserAPI {
	UserDto findByUsername(String username);
	UserDto getUserById(Integer userId);
	boolean createNew(UserDto user);
	List<UserDto> getUserByRole();
	boolean updateUser(Integer id, UserDto user);
	boolean chancePassword(Integer id, String password);
	
	boolean deleteUser(Integer userId);
}
