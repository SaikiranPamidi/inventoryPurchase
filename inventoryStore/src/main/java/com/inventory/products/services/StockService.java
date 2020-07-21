package com.inventory.products.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.inventory.products.dao.StockRepository;
import com.inventory.products.models.Stocks;

@Component
public class StockService {
	
	@Autowired
	StockRepository stockRepo;

	public void createStocks(Stocks st) {
		// TODO Auto-generated method stub
		System.out.println(st);
		stockRepo.saveAndFlush(st);
				
	}
	
	public List<Stocks> stocksAvailable() {
		// TODO Auto-generated method stub
		System.out.println("hit Store Service");
		List<Stocks> st= new ArrayList<Stocks>();
		System.out.println(st);
		st= stockRepo.findAll();
		if(st.isEmpty())
			return null;
		else
			return st;
	}

	
	public void removeProduct(int productID) {
		// TODO Auto-generated method stub
		
	}

	
	public Stocks updateStocks(Stocks stock) {
		
		// TODO Auto-generated method stub
		stockRepo.save(stock);
		Stocks st= stockRepo.getOne(stock.getId());
		return st;
	}
	
	public Stocks updateQuantityStocks(Stocks stock) {
		
		// TODO Auto-generated method stub
		//Optional<Stocks> stock = stockRepo.findById(stock.get().getId());
		//Stocks st=null;
		Optional<Stocks> updateStock =null;
		if(stock!=null) {						
			stockRepo.saveAndFlush(stock);
			updateStock = stockRepo.findById(stock.getId());
		}
		if(updateStock.isPresent())
			return updateStock.get();
		else
			return null;
	}


	

}
