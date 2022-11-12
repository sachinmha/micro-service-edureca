/**
 * 
 */
package com.edu.catalogms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.catalogms.model.Product;

/**
 * @author Administrator
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	 Optional<Product> findByCode(String code);
}
