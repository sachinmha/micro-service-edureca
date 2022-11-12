package com.edu.inventoryms.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.inventoryms.model.InventoryItem;
import com.edu.inventoryms.services.InventoryServices;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class InventoryController {
	
	@Autowired private InventoryServices services;
	private ResponseEntity responseEntity;
	
	@GetMapping("/api/inventory/{productCode}")
	@HystrixCommand
    public ResponseEntity<InventoryItem> findInventoryByProductCode(@PathVariable("productCode") String productCode) {
        Optional<InventoryItem> inventoryItem = services.findByProductCode(productCode);
        if(inventoryItem.isPresent()) {
            return new ResponseEntity(inventoryItem,HttpStatus.OK);
        } else {
            return new ResponseEntity<InventoryItem>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/inventory")
    @HystrixCommand
    public List<InventoryItem> getInventory() {
        return services.findAll();
    }
    
    @PostMapping("/api/inventory/orders")
    @HystrixCommand
    public ResponseEntity<InventoryItem> updateQuantity(@RequestParam ("id") Long id,@RequestParam("quantity") Integer quantity){
    	InventoryItem item =services.updateOrderedQuantity(id, quantity);
    	
    	if(item!=null) {
    		responseEntity= new ResponseEntity<InventoryItem>(item,HttpStatus.ACCEPTED);
    	}else {
    		responseEntity=new ResponseEntity<InventoryItem>(item,HttpStatus.NOT_ACCEPTABLE);
    	}
    	
    	return responseEntity;
    }
    
   

}
