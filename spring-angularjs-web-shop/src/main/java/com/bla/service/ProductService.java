package com.bla.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bla.entity.Product;

public interface ProductService {

	List<Product> findAll();
	
	Page<Product> findByEnabled(int page);
	
	Product findOne(Long id);
	
	Product save(Product product);
	
	void delete(Long id);
}
