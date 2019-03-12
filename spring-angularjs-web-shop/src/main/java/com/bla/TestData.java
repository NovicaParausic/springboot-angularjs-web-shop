package com.bla;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bla.entity.Order;
import com.bla.entity.Product;
import com.bla.repository.ProductRepository;
import com.bla.service.OrderService;

@Component
public class TestData {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderService cartItemService;
	
	@PostConstruct
	public void init(){
		for(int i = 0; i<15; i++){
			Product prod = new Product("car" + i, 50 + i, 100 + i, true);
			prod = productRepository.save(prod);
		
			Long l = new Long(i);
			Order order = new Order(l);
			cartItemService.save(order);
		
		}
	}
}
