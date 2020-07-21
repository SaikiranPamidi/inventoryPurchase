package com.inventory.products.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.inventory.products.models.Stocks;

public interface StockRepository extends JpaRepository<Stocks,Integer>{
	
    public List<Stocks> findById(@Param("id") Long id);


}
