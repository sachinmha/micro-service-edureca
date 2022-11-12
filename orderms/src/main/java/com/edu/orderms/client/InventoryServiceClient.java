package com.edu.orderms.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.edu.orderms.dto.ProductInventoryResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


public class InventoryServiceClient {
	private static final Logger log = LoggerFactory.getLogger(InventoryServiceClient.class);
	
	@Autowired private RestTemplate restTemplate;
	
	@Autowired private InventoryServiceFeignClient inventoryServiceFeignClient;
	
	
	private static final String INVENTORY_API_PATH = "http://inventory-service/api/";
	
	@HystrixCommand(fallbackMethod = "getUpdatedProductInventory", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000") })
	public ProductInventoryResponse updateOrderedQuantity(Long id,Integer quantity) {
		MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<String, String>();
        requestEntity.add("clientFlag", "");
        requestEntity.add("id", String.valueOf(id));
        requestEntity.add("quantity", String.valueOf(quantity));
		
        ResponseEntity<ProductInventoryResponse> productInventoryResponse= restTemplate.postForObject(INVENTORY_API_PATH+"", requestEntity,ResponseEntity.class);
        
        if(productInventoryResponse.equals(HttpStatus.ACCEPTED)) {
        	return productInventoryResponse.getBody();
        }else if (productInventoryResponse.equals(HttpStatus.NOT_ACCEPTABLE)) {
        	return new ProductInventoryResponse();
        }
		return null;
	}
	
	
	ProductInventoryResponse getUpdatedProductInventory(Long id,Integer quantity){
		log.info("fallbackMethod: {}");
		ProductInventoryResponse response =new ProductInventoryResponse();
		response.setProductCode("");
		return response;
	}
	
	

}
