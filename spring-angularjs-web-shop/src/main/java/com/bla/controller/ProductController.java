
package com.bla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bla.entity.Product;
import com.bla.service.ProductService;

@RestController
@RequestMapping(value = "/bla/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> getEnabledProducts(@RequestParam(value="page", defaultValue = "0") int page){
		List<Product> ret = productService.findByEnabled(page);
		
		if(CollectionUtils.isEmpty(ret)){
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@GetMapping(value = "/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> ret = productService.findAll();
		
		if(CollectionUtils.isEmpty(ret)){
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id){
		Product ret = productService.findOne(id);
		
		if(ret == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping(value = "/{id}/enabled/{enabled}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> toggleEnabledProduct(@PathVariable("id") Long id, @PathVariable("enabled") Boolean enabled ){
		Product ret = productService.findOne(id);
		
		if(ret == null) {
			return ResponseEntity.notFound().build();
		} else {
			ret.setEnabled(enabled);
			productService.save(ret);
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> addProduct(@RequestBody Product newProduct){
		Product saved = productService.save(newProduct);
		
		if(saved == null){
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(saved);
		}
	}
	
	@PutMapping(value="/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updateProduct){
		Product product = productService.findOne(id);
		
		if(product == null || (updateProduct.getId() != null && updateProduct.getId().equals(id))){
			return ResponseEntity.badRequest().build();
		} else {
			Product saved = productService.save(updateProduct);
			return ResponseEntity.ok(saved);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		Product product = productService.findOne(id);
		
		if(product == null) {
			return ResponseEntity.badRequest().build();
		} else {
			productService.delete(id);
			return ResponseEntity.ok().build();
		}
	}	
}