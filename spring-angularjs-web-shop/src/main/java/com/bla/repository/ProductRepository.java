package com.bla.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bla.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	@Query("SELECT p FROM Product p WHERE p.enabled = 'true'")
	Page<Product> findByEnabled(Pageable pageable);
	
	List<Product> findAll();
}