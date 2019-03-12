
package com.bla.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bla.model.CartItem;
import com.bla.service.CartService;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService{

	private Map<Long, CartItem> map = new HashMap<>();

	@Override
	@Transactional
	public CartItem saveItem(CartItem item) {
		return map.put(item.getCode(), item);
	}

	@Override
	public List<CartItem> getCartItems() {
		return new ArrayList<>(map.values());
	}

	@Override
	public void removeItems() {
		map.clear();
	}

	@Override
	public CartItem removeCartItem(Long code) {
		return map.remove(code);
	}
}
