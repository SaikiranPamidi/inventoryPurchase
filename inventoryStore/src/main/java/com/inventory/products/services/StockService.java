package com.inventory.products.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.products.dao.StockRepository;
import com.inventory.products.exception.ProductNotFoundException;
import com.inventory.products.models.Stocks;
import com.inventory.products.models.StocksDTO;

@Component
public class StockService {
	
	Logger logger = LogManager.getLogger(StockService.class);
	
	@Autowired
	StockRepository stockRepo;
	
	@Autowired
    ModelMapper modelMapper;

	public String createStocks(Stocks st) {
		
		logger.info(st);
		stockRepo.saveAndFlush(st);
		return "added to store";		
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
		logger.info(stock);
		logger.info(updateStock);
		return updateStock;		
	}


	public Stocks convertToEntity(StocksDTO stocksDto)  {
		if(stocksDto!=null) {
			logger.info(stocksDto);
			return modelMapper.map(stocksDto, Stocks.class);
		}
		else
		{
			logger.info(stocksDto);
			return null;
		}
	}
	

}
