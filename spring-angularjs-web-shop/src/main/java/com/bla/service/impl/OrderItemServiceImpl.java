package com.bla.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bla.entity.OrderItem;
import com.bla.model.CartItem;
import com.bla.repository.OrderItemRepository;
import com.bla.service.OrderItemService;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService{

	@Autowired
	private OrderItemRepository orderRepo;

	@Override
	@Transactional
	public List<OrderItem> saveItems(List<CartItem> items) {
		List<OrderItem> ret = new ArrayList<OrderItem>();
		Date date = new Date();
		
		for(CartItem item : items){
			OrderItem oItem = new OrderItem();
			
			oItem.setName(item.getName());
			oItem.setProductId(item.getCode());
			oItem.setPrice(item.getPrice());
			oItem.setBuyingPrice(item.getBuyingPrice());
			oItem.setQunatity(item.getQuantity());
			oItem.setMargin(item.getPrice() - item.getBuyingPrice());
			oItem.setDate(date);
			
			orderRepo.save(oItem);
			ret.add(oItem);
		}
		
		return ret;
	}

	@Override
	public List<OrderItem> getItems() {
		return orderRepo.findAll();
	}
}
