package com.inventory.products.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.products.dao.StockRepository;
import com.inventory.products.exception.NoDataFoundException;
import com.inventory.products.exception.ProductNotFoundException;
import com.inventory.products.models.Stocks;

@Component
public class StockService {
	
	Logger logger = LogManager.getLogger(StockService.class);
	
	@Autowired
	StockRepository stockRepo;

	public void createStocks(Stocks st) {
		
		logger.info(st);
		stockRepo.saveAndFlush(st);
				
	}
	
	public List<Stocks> stocksAvailable() {
		logger.info("hit Store Service");
		List<Stocks> st= new ArrayList<>();
		logger.info(st);
		st= stockRepo.findAll();
		return st;
	}


	
	public Stocks updateStocks(Stocks stock) {	
		stockRepo.save(stock);
		return stockRepo.getOne(stock.getId());
	}
	
	public Stocks updateQuantityStocks(Stocks stock) {
		
		Stocks updateStock = null;
		if(stock!=null) {						
			stockRepo.saveAndFlush(stock);
			updateStock = stockRepo.findById(stock.getId()).orElseThrow(() -> new ProductNotFoundException(stock.getId()));			
		}
		
		return updateStock;		
	}


	

}
