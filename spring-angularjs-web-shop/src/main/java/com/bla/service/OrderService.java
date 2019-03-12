package com.bla.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bla.entity.Order;
import com.bla.model.CartItem;

public interface OrderService {

	Page<Order> getOrders(int page);
	
	Order getOrder(Long id);
	
	Order save(Order order);
	
	Order save(Long userId, List<CartItem> items);
	
	void delete(Order order);
}
