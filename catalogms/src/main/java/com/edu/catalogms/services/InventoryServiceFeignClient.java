package com.edu.catalogms.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.edu.catalogms.dto.ProductInventoryResponse;

@FeignClient(name = "inventory-service")
public interface InventoryServiceFeignClient {
	
	@GetMapping("/api/inventory")
    List<ProductInventoryResponse> getInventoryLevels();

    @GetMapping("/api/inventory/{productCode}")
    List<ProductInventoryResponse> getInventoryByProductCode(String productCode);

}
