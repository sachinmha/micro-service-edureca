package com.edu.orderms.services;

import java.util.Optional;

import com.edu.orderms.model.Order;

public interface OrderServices {
	
	
	Order save(Order order);
	Optional<Order> findById(Long id);

}
