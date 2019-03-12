package com.bla.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bla.security.enitity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
