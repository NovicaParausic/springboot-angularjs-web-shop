
package com.bla.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bla.security.enitity.User;
import com.bla.security.service.UserService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<User>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page){
		Page<User> ret = userService.getUsers(page);
		
		return ResponseEntity.ok(ret);
	}
	
	@RequestMapping(value = "/users/{id}/enabled/{enabled}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> enableUsers(@PathVariable("id") Long id, @PathVariable("enabled") Boolean enabled){
		User ret = userService.findById(id);
		
		if(ret == null){
			return ResponseEntity.badRequest().build();
		} else {
			ret.setEnabled(enabled);
			userService.save(ret);
			
			return ResponseEntity.ok(ret);
		}
		
	}
}
