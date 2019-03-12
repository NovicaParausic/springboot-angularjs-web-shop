package com.bla.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.bla.security.enitity.Authority;
import com.bla.security.enitity.User;

public class JwtUserFactory {

	private JwtUserFactory(){}
	
	public static JwtUser create(User user){
		return new JwtUser(
				user.getId(),
				user.getUsername(),
				user.getFirstname(),
				user.getLastname(),
				user.getPassword(),
				user.getEmail(),
				mapToGrantedAuthorities(user.getAuthorities()),
				user.getEnabled(),
				user.getLastPasswordResetDate()
			);
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities){
		List<GrantedAuthority> ret = new ArrayList<>();
		
		for(Authority auth : authorities){
			ret.add(new SimpleGrantedAuthority(auth.getName().name()));
		}
		return ret;
	}
}
