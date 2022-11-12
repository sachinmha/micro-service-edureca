package com.edu.catalogms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edu.catalogms.dto.ProductInventoryResponse;
import com.edu.catalogms.util.MyThreadLocalsHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class InventoryServiceClient {

	private static final Logger log = LoggerFactory.getLogger(InventoryServiceClient.class);
	@Autowired 
	private  RestTemplate restTemplate;
	
	@Autowired
	private InventoryServiceFeignClient inventoryServiceFeignClient;
	
	// TODO; move this to config file
	//private static final String INVENTORY_API_PATH = "http://inventory-service/api/";
	private static final String INVENTORY_API_PATH = "http://localhost:8084/api/";

	

	@HystrixCommand(fallbackMethod = "getDefaultProductInventoryLevels", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000") })
	public List<ProductInventoryResponse> getProductInventoryLevels() {
		return this.inventoryServiceFeignClient.getInventoryLevels();
	}

	@SuppressWarnings("unused")
	List<ProductInventoryResponse> getDefaultProductInventoryLevels() {
		log.info("Returning default product inventory levels");
		return new ArrayList<ProductInventoryResponse>();
	}

	@HystrixCommand(fallbackMethod = "getDefaultProductInventoryByCode")
	public Optional<ProductInventoryResponse> getProductInventoryByCode(String productCode) {
		log.info("CorrelationID: " + MyThreadLocalsHolder.getCorrelationId());
		
		ResponseEntity<ProductInventoryResponse> itemResponseEntity = restTemplate
				.getForEntity(INVENTORY_API_PATH + "inventory/{code}", ProductInventoryResponse.class, productCode);

		if (itemResponseEntity.getStatusCode() == HttpStatus.OK) {
			Integer quantity = itemResponseEntity.getBody().getAvailableQuantity();
			log.info("Available quantity: " + quantity);
			return Optional.ofNullable(itemResponseEntity.getBody());
		} else {
			log.error("Unable to get inventory level for product_code: " + productCode + ", StatusCode: "+ itemResponseEntity.getStatusCode());
			return Optional.empty();
		}
	}
	
	
	 @SuppressWarnings("unused")
	    Optional<ProductInventoryResponse> getDefaultProductInventoryByCode(String productCode) {
	        log.info("Returning default ProductInventoryByCode for productCode: "+productCode);
	        log.info("CorrelationID: "+ MyThreadLocalsHolder.getCorrelationId());
	        ProductInventoryResponse response = new ProductInventoryResponse();
	        response.setProductCode(productCode);
	        response.setAvailableQuantity(50);
	        return Optional.ofNullable(response);
	    }
	
	
}
