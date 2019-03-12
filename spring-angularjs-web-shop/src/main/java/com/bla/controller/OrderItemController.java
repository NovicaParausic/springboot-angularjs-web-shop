package com.bla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bla.entity.OrderItem;
import com.bla.model.CartItem;
import com.bla.service.OrderItemService;

@RestController
@RequestMapping
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;
	
	@GetMapping(value="/bla/orderItems")
	public ResponseEntity<List<OrderItem>> getItems(){
		
		List<OrderItem> ret = orderItemService.getItems();
		
		if(ret == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(ret, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/api/orders")
	public ResponseEntity<List<OrderItem>> buyItems(@RequestBody List<CartItem> items){
		List<OrderItem> ret = orderItemService.saveItems(items);
		
		if(ret == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(ret, HttpStatus.NOT_FOUND);
		}
	}
}
