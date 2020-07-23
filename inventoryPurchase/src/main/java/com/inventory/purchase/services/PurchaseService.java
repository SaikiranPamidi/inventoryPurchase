package com.inventory.purchase.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.inventory.purchase.conroller.PurchaseController;
import com.inventory.purchase.dao.PurchaseRepo;
import com.inventory.purchase.model.Purchase;
import com.inventory.purchase.model.Stocks;

@Component
public class PurchaseService {

	Logger logger = LogManager.getLogger(PurchaseService.class);
	
	@Autowired
	PurchaseRepo purchaseRepo;
	
	@Autowired
	private InvStoreService storeServ;

	public List<Purchase> purchasedProducts() {
		// TODO Auto-generated method stub
		List<Purchase> purchasedList = purchaseRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return purchasedList;
	}

	public void purchaseProducts(Purchase product) {
		System.out.println(product);
		List<Stocks> stockList = new ArrayList<Stocks>();
		
		try {
			if(storeServ.getStocksAvailable()!=null)
				stockList = storeServ.getStocksAvailable();
			stockList.forEach(x->logger.info("null check "+x));
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("exception occured ");
		}
		

		if (stockList.isEmpty()) {
			Stocks st = new Stocks();
			st.setProductId(product.getProductId());
			st.setProductName(product.getProductName());
			st.setStockAvailable(product.getQuantity());
			Random rand = new Random();
			int rand_int1 = rand.nextInt(1000);
			st.setStockID(rand_int1);
			logger.info(st);
			//ResponseEntity<String> entity=storeServ.createProduct(st);
			//if(entity.getBody().equalsIgnoreCase("Purchased Stocks to Store Successfull"))
			//	purchaseRepo.saveAndFlush(product);
			
		} else {
			stockList.forEach(x -> {
				logger.info("Stock Data : " + x);
				if (x.getProductName().equalsIgnoreCase(product.getProductName())) {
					logger.info(x);
					int orderQuantity = 0;
					orderQuantity = product.getQuantity();
					if (orderQuantity <= x.getStockAvailable()) {
						int latestQuantity = x.getStockAvailable() + orderQuantity;
						Stocks st = x;
						st.setStockAvailable(latestQuantity);

						Stocks updatedStock = storeServ.updateStocksQuantity(st);
						logger.info("Updated latest quantity" + updatedStock);
						if (updatedStock.getStockAvailable() == latestQuantity) {
							purchaseRepo.saveAndFlush(product);
							
						}
					}
				} else {
					
					Stocks st = new Stocks();
					st.setProductId(product.getProductId());
					st.setProductName(product.getProductName());
					st.setStockAvailable(product.getQuantity());
					Random rand = new Random();
					int rand_int1 = rand.nextInt(1000);
					st.setStockID(rand_int1);
					logger.info(st);
					ResponseEntity<String> entity=storeServ.createProduct(st);
					if(entity.getBody().equalsIgnoreCase("Purchased Stocks to Store Successfull"))
						purchaseRepo.saveAndFlush(product);
					
				}
				
				
			});

		}
		
	}

	public boolean delectPurchaseProducts(int id) {
	
		Purchase product =purchaseRepo.getOne(id);
		if(product!=null) {
			purchaseRepo.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

}
