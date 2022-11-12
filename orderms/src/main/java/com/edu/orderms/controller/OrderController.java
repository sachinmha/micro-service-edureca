package com.edu.orderms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.orderms.model.Order;
import com.edu.orderms.services.OrderServices;

@RestController
public class OrderController {

	@Autowired
	private OrderServices service;

	@PostMapping("/api/orders")
	public Order createOrder(@RequestBody Order order) {
		return service.save(order);
	}

	@GetMapping("/api/orders/{id}")
	public Optional<Order> findOrderById(@PathVariable Long id) {
		return service.findById(id);
	}

}
