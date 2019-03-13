package com.bla.security.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bla.common.TimeProvider;
import com.bla.security.dto.UserDto;
import com.bla.security.enitity.Authority;
import com.bla.security.enitity.User;
import com.bla.security.repository.UserRepository;
import com.bla.security.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private static Long USER_ROLE_ID = 1L;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User findUserByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	@Transactional
	public User createUserAccount(UserDto dto){
		User registered = new User();
		String password = passwordEncoder.encode(dto.getPassword());
		
		registered.setUsername(dto.getUsername());
		registered.setPassword(password);
		registered.setFirstname(dto.getFirstname());
		registered.setLastname(dto.getLastname());
		registered.setEmail(dto.getEmail());
		registered.setEnabled(true);
		registered.setLastPasswordResetDate(TimeProvider.now());
		registered.setAuthorities(setAuthority());
		
		return userRepository.save(registered);
		
	}
	
	@Override
	public Page<User> getUsers(int page) {
		return userRepository.findByIdGreaterThen(PageRequest.of(page, 5));
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	private List<Authority> setAuthority(){
		Authority auth = new Authority(USER_ROLE_ID);
		return Collections.singletonList(auth);
	}
}
