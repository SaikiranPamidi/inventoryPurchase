package com.inventory.products.controller;


import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.products.models.Stocks;
import com.inventory.products.services.StockService;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
	
	Logger logger = LogManager.getLogger(HomeController.class);
	
	@Autowired
	StockService st;
	
	@GetMapping("")
	public String home() {
	 return "welcome to Invenotry Application";
	}
	
	//@Cacheable(value = "stocks")
	@GetMapping("/getStockList")
	public List<Stocks> getStocksAvailable(){
		logger.info("hitting Stock Services");
		return st.stocksAvailable();		
	}
	
	@PostMapping("/createProduct")
	public ResponseEntity<String> createProduct(@RequestBody Stocks stock) {
		logger.info(stock);
		st.createStocks(stock);
	 return new ResponseEntity<String>("Purchased Stocks to Store Successfull", HttpStatus.CREATED);
	}
	
	
	@PutMapping("/updateProduct")
	public @ResponseBody Stocks updateProduct(@RequestBody Stocks stock) {
		logger.info(stock);
		Stocks result=st.updateStocks(stock);
		
	 return result;
	}
	
	@PutMapping("/updateStockQuantity")
	public @ResponseBody Stocks updateProductQuantity(@RequestBody Stocks stock) {
		System.out.println("quantity updation stock id :"+stock);
		Stocks result=st.updateQuantityStocks(stock);
		
	 return result;
	}

}
