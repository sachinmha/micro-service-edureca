package com.edu.catalogms.services;

import java.util.List;
import java.util.Optional;

import com.edu.catalogms.model.Product;

public interface ProductServices {
	Optional<Product> findByCode(String code);
	List<Product> findAll();
	public List<Product> findAllProducts();
}
