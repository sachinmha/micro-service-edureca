package com.edu.orderms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.edu.orderms.dto.ProductInventoryResponse;

@FeignClient(name = "inventory-service")
public interface InventoryServiceFeignClient {
	

@PostMapping("/api/inventory/orders")
ProductInventoryResponse updateOrderedInventoryQuantity(Long id,Integer quantity);

}
