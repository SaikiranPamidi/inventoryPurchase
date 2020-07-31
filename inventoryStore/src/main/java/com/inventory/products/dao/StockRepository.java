package com.inventory.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventory.products.models.Stocks;

public interface StockRepository extends JpaRepository<Stocks,Integer>{
	

}
