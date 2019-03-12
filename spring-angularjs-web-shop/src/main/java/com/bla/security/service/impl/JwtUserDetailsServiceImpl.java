package com.bla.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bla.security.JwtUserFactory;
import com.bla.security.enitity.User;
import com.bla.security.repository.UserRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);
		
		if(user == null){
			throw new UsernameNotFoundException(String.format("No user found with username: '%s'." , username));
		} else {
			return JwtUserFactory.create(user);
		}
	}
}
