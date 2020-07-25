package com.inventory.purchase.services;

import java.security.SecureRandom;
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

	private Random rand = new Random();

	public List<Purchase> purchasedProducts() {
		
		return purchaseRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
	}

	public void purchaseProducts(Purchase product) {
		logger.info(product);
		
		List<Stocks> stockList = storeServ.getStocksAvailable();

		if (stockList.isEmpty()) {
			Stocks st = new Stocks();
			st.setProductId(product.getProductId());
			st.setProductName(product.getProductName());
			st.setStockAvailable(product.getQuantity());
			int randInt = rand.nextInt(1000);
			st.setStockID(randInt);
			logger.info(st);
			logger.info("top if");
			
			ResponseEntity<String> entityCreate = storeServ.createProduct(st);
			logger.info(entityCreate.getBody());

			if (entityCreate.getBody().equalsIgnoreCase("Purchased Stocks to Store Successfull"))
				purchaseRepo.saveAndFlush(product);
			
		} else {
			List<Stocks> lt = new ArrayList<>();
			
			stockList.forEach(x -> {
							logger.info("Stock Data : ");
							logger.info(x);
							if (x.getProductName().equalsIgnoreCase(product.getProductName())) {
								logger.info(x);
								
									int latestQuantity = x.getStockAvailable() + product.getQuantity();
									Stocks st = x;
									st.setStockAvailable(latestQuantity);
			
									Stocks updatedStock = storeServ.updateStocksQuantity(st);
									logger.info("Updated latest quantity");
									logger.info(updatedStock);
									if (updatedStock.getStockAvailable() == latestQuantity) {
										purchaseRepo.saveAndFlush(product);
										
									}
							
								lt.add(st);
							} 								
						});
			
			if(lt.isEmpty())
			{
				Stocks st = new Stocks();
				st.setProductId(product.getProductId());
				st.setProductName(product.getProductName());
				st.setStockAvailable(product.getQuantity());
				int randInt = rand.nextInt(1000);
				st.setStockID(randInt);
				logger.info(st);
				ResponseEntity<String> entity=storeServ.createProduct(st);
				if(entity.getBody().equalsIgnoreCase("Purchased Stocks to Store Successfull"))
					purchaseRepo.saveAndFlush(product);
			}

		}

	}

	public boolean delectPurchaseProducts(int id) {

		Purchase product = purchaseRepo.getOne(id);
		if (product != null) {
			purchaseRepo.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

}
