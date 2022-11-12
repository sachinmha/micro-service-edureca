package com.edu.inventoryms.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.inventoryms.model.InventoryItem;
import com.edu.inventoryms.repository.InventoryItemRepository;

import lombok.AllArgsConstructor;
@Service
public class InventoryServicesImpl implements InventoryServices  {
	private static final Logger log = LoggerFactory.getLogger(InventoryServicesImpl.class);
	@Autowired private InventoryItemRepository repo;

	public Optional<InventoryItem> findByProductCode(String productCode) {
		log.info("productCode {}"+productCode);
		return repo.findByProductCode(productCode);
	}

	public List<InventoryItem> findAll() {
		return repo.findAll();
	}

	public InventoryItem updateOrderedQuantity(Long id, Integer quantity) {
		log.info("id {}"+id+",quantity{}"+quantity);
		InventoryItem product=null;
		Optional<InventoryItem> item =repo.findById(id);
		
		if(item.isPresent()) {
			product = item.get();
			Integer availableQuantity=product.getAvailableQuantity()-quantity;
			product.setAvailableQuantity(availableQuantity);
			repo.save(product);
		}
		
		return product;
	}
	
}
