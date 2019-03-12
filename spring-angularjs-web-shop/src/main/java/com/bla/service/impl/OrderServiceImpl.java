package com.bla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bla.entity.Order;
import com.bla.entity.OrderItem;
import com.bla.model.CartItem;
import com.bla.repository.OrderItemRepository;
import com.bla.repository.OrderRepository;
import com.bla.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public Page<Order> getOrders(int page) {
		return orderRepository.findAll(PageRequest.of(page, 5));
	}

	@Override
	public Order getOrder(Long id) {
		return orderRepository.findById(id).orElse(null);
	}
		
	@Override
	public Order save(Long userId, List<CartItem> items) {
		double totalValue = 0;
		double totalPrice = 0;
		double totalMargin = 0;
		
		Order order = new Order();
		order = orderRepository.save(order);
		
		for(CartItem cartItem : items){
			OrderItem orderItem = new OrderItem(order.getId());
			
			double value = cartItem.getBuyingPrice() * cartItem.getQuantity();
			orderItem.setBuyingPrice(value);
			totalValue += value;
			
			double price = cartItem.getPrice() * cartItem.getQuantity();
			orderItem.setPrice(price);
			totalPrice += price;
			
			double margin = price - value;
			orderItem.setMargin(margin);
			totalMargin += margin;
			
			orderItem.setUserId(userId);
			orderItem.setProductId(cartItem.getCode());
			orderItem.setName(cartItem.getName());
			orderItem.setQunatity(cartItem.getQuantity());
			
			orderItemRepository.save(orderItem);
		}
		
		order.setValue(totalValue); 
		order.setPrice(totalPrice);
		order.setMargin(totalMargin);
		
		order = orderRepository.save(order);
		
		return order;
	}
	
	@Override
	public void delete(Order order) {
		orderRepository.delete(order);
	}

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	
}
