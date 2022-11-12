package com.edu.orderms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edu.orderms.client.InventoryServiceClient;
import com.edu.orderms.dto.ProductInventoryResponse;
import com.edu.orderms.model.Order;
import com.edu.orderms.repositories.OrderRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderServices{
	
	private OrderRepository orderRepository;
	private InventoryServiceClient inventoryServiceClient;
	

	public Order save(Order entity) {
		System.out.println("Save {}");
		
		return orderRepository.save(entity);
	}

	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}
	
	public List<ProductInventoryResponse> madeOrder(Order entity) {
		List<ProductInventoryResponse> oderedItems = new ArrayList<>();
		oderedItems = entity.getItems().stream().map(item -> {
			ProductInventoryResponse p= new ProductInventoryResponse();
			 p=this.inventoryServiceClient.updateOrderedQuantity(item.getProductId(),item.getQuantity());
			 return p;
		}).collect(Collectors.toList());
	return oderedItems;
	}
	

}
