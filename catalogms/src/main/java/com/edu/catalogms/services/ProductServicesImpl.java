package com.edu.catalogms.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.catalogms.dto.ProductInventoryResponse;
import com.edu.catalogms.model.Product;
import com.edu.catalogms.repository.ProductRepository;

@Service

public class ProductServicesImpl implements ProductServices{
	private static final Logger log = LoggerFactory.getLogger(ProductServicesImpl.class);
	@Autowired private ProductRepository productRepository;
	@Autowired private InventoryServiceClient inventoryServiceClient;
	

	public Optional<Product> findByCode(String code) {
		return productRepository.findByCode(code);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        final Map<String, Integer> inventoryLevels = getInventoryLevelsWithFeignClient();
        final List<Product> availableProducts = products.stream()
                .filter(p -> inventoryLevels.get(p.getCode()) != null && inventoryLevels.get(p.getCode()) > 0)
                .collect(Collectors.toList());
        return availableProducts;
    }
	
	private Map<String, Integer> getInventoryLevelsWithFeignClient() {
        log.info("Fetching inventory levels using FeignClient");
        Map<String, Integer> inventoryLevels = new HashMap<>();
        List<ProductInventoryResponse> inventory = inventoryServiceClient.getProductInventoryLevels();
        for (ProductInventoryResponse item: inventory){
            inventoryLevels.put(item.getProductCode(), item.getAvailableQuantity());
        }
        log.debug("InventoryLevels: {}", inventoryLevels);
        return inventoryLevels;
    }
}
