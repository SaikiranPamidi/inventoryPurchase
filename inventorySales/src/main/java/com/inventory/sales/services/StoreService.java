package com.inventory.sales.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.inventory.sales.models.Order;
import com.inventory.sales.models.Stocks;

//@FeignClient(url = "http://skinventorystore-env.eba-cs7wdrvr.us-east-2.elasticbeanstalk.com",name="INVENTORY-STORE")
@FeignClient(url = "http://localhost:9081",name="INVENTORY-STORE")
public interface StoreService {

	@GetMapping("/api/v1/getStockList")
	public List<Stocks> getStocksAvailable();	
	
	@PutMapping("/api/v1/updateStockQuantity/")
	public Stocks updateStocksQuantity(Stocks st);

	
	
}
