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
import com.inventory.products.models.StocksDTO;
import com.inventory.products.services.StockService;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
	
	Logger logger = LogManager.getLogger(HomeController.class);
	
	@Autowired
	StockService st;
	
	
	
	@GetMapping("")
	public ResponseEntity<String> home() {
	 return new ResponseEntity<>("welcome to Invenotry Application", HttpStatus.OK);
	}
	
	//@Cacheable(value = "stocks")
	@GetMapping("/getStockList")
	public ResponseEntity<List<Stocks>>  getStocksAvailable(){
		logger.info("hitting Stock Services");
		List<Stocks> stocks=st.stocksAvailable();
		return new ResponseEntity<>(stocks, HttpStatus.OK);
	}
	
	@PostMapping("/createProduct")
	public ResponseEntity<String> createProduct(@RequestBody StocksDTO stocksDTO) {
		logger.info(stocksDTO);
		Stocks stock = st.convertToEntity(stocksDTO);
		st.createStocks(stock);
	 return new ResponseEntity<>("Purchased Stocks to Store Successfull", HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/updateProduct")
	public @ResponseBody Stocks updateProduct(@RequestBody StocksDTO stocksDTO) {
		logger.info("Updating product of");	
		logger.info(stocksDTO);
		Stocks stock = st.convertToEntity(stocksDTO);
	    return st.updateStocks(stock);
	}
	
	@PutMapping("/updateStockQuantity")
	public @ResponseBody Stocks updateProductQuantity(@RequestBody StocksDTO stocksDTO) {
		logger.info("Updating product Quantity of");	
		logger.info(stocksDTO);
		Stocks stock = st.convertToEntity(stocksDTO);
		return st.updateQuantityStocks(stock);
	}

	
}
