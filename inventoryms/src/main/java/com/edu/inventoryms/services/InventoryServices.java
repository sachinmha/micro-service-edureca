package com.edu.inventoryms.services;

import java.util.List;
import java.util.Optional;

import com.edu.inventoryms.model.InventoryItem;

public interface InventoryServices {
	
	Optional<InventoryItem> findByProductCode(String productCode);
	List<InventoryItem> findAll();
	InventoryItem updateOrderedQuantity(Long id,Integer quantity);

}
