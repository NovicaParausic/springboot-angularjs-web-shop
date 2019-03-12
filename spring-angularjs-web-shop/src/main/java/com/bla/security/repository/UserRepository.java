package com.bla.security.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bla.security.enitity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.id > 1")
	Page<User> findByIdGreaterThen(Pageable pageable);
	
	List<User> findAll();
}
