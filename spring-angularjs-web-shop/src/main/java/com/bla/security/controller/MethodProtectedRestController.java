package com.bla.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/protected")
public class MethodProtectedRestController {

	@GetMapping(value = "/admin", produces = "application/json")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> getProtectedGreeting(){
		return ResponseEntity.ok("Greetings from admin protected method"); 
	}
	
	@GetMapping(value = "/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> getProtectedGreetingUser(){
		return ResponseEntity.ok("Greetings from user protected method"); 
	}
}
