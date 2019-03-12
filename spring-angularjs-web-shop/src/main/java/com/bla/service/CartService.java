package com.bla.service;

import java.util.List;

import com.bla.model.CartItem;

public interface CartService {

	CartItem saveItem(CartItem item);
	
	List<CartItem> getCartItems();
	
	CartItem removeCartItem(Long code);
	
	void removeItems();
}
