package com.inventory.sales.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.sales.models.Order;


public interface OrderRepository extends JpaRepository<Order, Integer>{

	
	
}
