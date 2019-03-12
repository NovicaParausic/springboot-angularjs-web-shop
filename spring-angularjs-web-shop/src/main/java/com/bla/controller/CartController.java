
package com.bla.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bla.entity.Order;
import com.bla.entity.Product;
import com.bla.model.CartItem;
import com.bla.security.JwtTokenUtil;
import com.bla.security.JwtUser;
import com.bla.service.CartService;
import com.bla.service.OrderService;
import com.bla.service.ProductService;

@RestController
@RequestMapping(value = "/api/cart")
public class CartController {

	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping(value = "/buy")
	public ResponseEntity<Order> buyProducts(HttpServletRequest request){
		String username = jwtTokenUtil.getUsernameFromRequest(request);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		
		if(user == null){
			return ResponseEntity.badRequest().build();
		} 
		
		Long userId = user.getId();
		List<CartItem> items = cartService.getCartItems();
		
		if(CollectionUtils.isEmpty(items)){
			return ResponseEntity.notFound().build();
		} else {
			Order order = orderService.save(userId, items);
			cartService.removeItems();
			
			return ResponseEntity.ok(order);
		}
			
		
	}
	
	@GetMapping
	public ResponseEntity<List<CartItem>> getItemsFromCart(){
		List<CartItem> items = cartService.getCartItems();
		
		if(CollectionUtils.isEmpty(items)){
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(items);
		}
	}
	
	@PostMapping
	public ResponseEntity<CartItem> saveItem(@RequestBody CartItem item){
		Product product = productService.findOne(item.getCode());
		
		if(product == null){
			return ResponseEntity.notFound().build();
		} else {
			cartService.saveItem(item);
			return ResponseEntity.ok(item);
		}
	}
	
	@DeleteMapping(value="/{code}")
	public ResponseEntity<CartItem> deleteItem(@PathVariable Long code){
		CartItem item = cartService.removeCartItem(code);
		
		if(item == null){
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(item);
		}
	}
	
	@DeleteMapping(value="/items")
	public void clearCart(@PathVariable String code){
		cartService.removeItems();
	}
}
