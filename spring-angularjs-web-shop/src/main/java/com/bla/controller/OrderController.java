package com.bla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bla.entity.Order;
import com.bla.service.OrderService;

@RestController
@RequestMapping(value="/bla/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public ResponseEntity<Page<Order>> getItems(@RequestParam(value = "page", defaultValue = "0") int page){
		Page<Order> ret = orderService.getOrders(page);
		
		if(ret == null){
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
}
