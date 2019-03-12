package com.bla.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bla.security.dto.UserDto;
import com.bla.security.enitity.User;
import com.bla.security.service.UserService;

@RestController
public class RegistrationController {

	@Autowired
	private UserService registrationService;
	
	@PostMapping(value = "/registration")
	public ResponseEntity<String> registerUser( @RequestBody final @Valid UserDto dto, BindingResult result){
		User registered = registrationService.findUserByUsername(dto.getUsername());
		
		if(result.hasErrors()){
			return ResponseEntity.badRequest().build();
		}
		
		if(registered != null){
			return ResponseEntity.unprocessableEntity().build();
		} else {
			registrationService.createUserAccount(dto);
			return ResponseEntity.ok().build();
		}
	}
}
