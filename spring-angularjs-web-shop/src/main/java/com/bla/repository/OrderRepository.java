package com.bla.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bla.entity.Order;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	List<Order> findAll();
}
