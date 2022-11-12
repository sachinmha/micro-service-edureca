package com.edu.orderms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.orderms.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
