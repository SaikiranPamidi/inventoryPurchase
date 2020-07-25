package com.inventory.products.controller;


import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<List<Stocks>>  getStocksAvailable(){
		logger.info("hitting Stock Services");
		List<Stocks> stocks=st.stocksAvailable();
		return new ResponseEntity<>(stocks, HttpStatus.OK);
	}
	
	@PostMapping("/createProduct")
	public ResponseEntity<String> createProduct(@RequestBody Stocks stock) {
		logger.info(stock);
		st.createStocks(stock);
	 return new ResponseEntity<>("Purchased Stocks to Store Successfull", HttpStatus.CREATED);
	}
	
	
	@PutMapping("/updateProduct")
	public @ResponseBody Stocks updateProduct(@RequestBody Stocks stock) {
		logger.info("Updating product of");	
		logger.info(stock);
	    return st.updateStocks(stock);
	}
	
	@PutMapping("/updateStockQuantity")
	public @ResponseBody Stocks updateProductQuantity(@RequestBody Stocks stock) {
		logger.info("Updating product Quantity of");	
		logger.info(stock);	
		return st.updateQuantityStocks(stock);
	}

}
