package com.bla.service;

import java.util.List;

import com.bla.entity.OrderItem;
import com.bla.model.CartItem;

public interface OrderItemService {

	List<OrderItem> saveItems(List<CartItem> items);
	
	List<OrderItem> getItems();
}
