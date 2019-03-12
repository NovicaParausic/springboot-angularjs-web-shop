package com.bla.security.service;

import org.springframework.data.domain.Page;

import com.bla.security.dto.UserDto;
import com.bla.security.enitity.User;

public interface UserService {

	Page<User> getUsers(int page);
	
	User findById(Long id);
	
	User save(User user);
	
	User findUserByUsername(String username);
	
	User createUserAccount(UserDto dto);
}
