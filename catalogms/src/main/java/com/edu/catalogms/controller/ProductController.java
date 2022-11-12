package com.edu.catalogms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.catalogms.exception.ProductNotFoundException;
import com.edu.catalogms.model.Product;
import com.edu.catalogms.services.ProductServices;

@RestController
@RequestMapping(value="/api/v1/products")
public class ProductController {
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired  private ProductServices productService;
	
	@GetMapping("/test")
	public String allAvailableProduct(){
		return "test";
	}
	@GetMapping("")
    public List<Product> allProducts(HttpServletRequest request) {
        log.info("Finding all products");
        String auth_header = request.getHeader("AUTH_HEADER");
        log.info("AUTH_HEADER: "+auth_header);
        return productService.findAllProducts();
    }

    @GetMapping("/{code}")
    public Product productByCode(@PathVariable String code) {
        log.info("Finding product by code :"+code);
        return productService.findByCode(code)
                .orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
    }
}
