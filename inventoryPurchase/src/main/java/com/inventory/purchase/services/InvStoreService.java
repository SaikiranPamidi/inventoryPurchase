package com.inventory.purchase.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.inventory.purchase.model.Stocks;


@FeignClient(url = "http://skinventorystore-env.eba-cs7wdrvr.us-east-2.elasticbeanstalk.com",name="INVENTORY-STORE" )
public interface InvStoreService {

	@GetMapping("/api/v1/getStockList")
	public List<Stocks> getStocksAvailable();	
	
	@PutMapping("/api/v1/updateStockQuantity/")
	public Stocks updateStocksQuantity(Stocks st);
	
	@PostMapping("/api/v1/createProduct")
	public Stocks createProduct(Stocks st);
	
}
