package com.inventory.purchase.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.inventory.purchase.dao.PurchaseRepo;
import com.inventory.purchase.model.Purchase;
import com.inventory.purchase.model.Stocks;

@Component
public class PurchaseService {

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
			stockList.forEach(x->System.out.println("null check "+x));
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("exception occured ");
		}
		

		if (stockList.isEmpty()) {
			Stocks st = new Stocks();
			st.setProductId(product.getProductId());
			st.setProductName(product.getProductName());
			st.setStockAvailable(product.getQuantity());
			Random rand = new Random();
			int rand_int1 = rand.nextInt(1000);
			st.setStockID(rand_int1);
			System.out.println(st);
			storeServ.createProduct(st);
			purchaseRepo.saveAndFlush(product);

		} else {
			stockList.forEach(x -> {
				System.out.println("Stock Data : " + x);
				if (x.getProductName().equalsIgnoreCase(product.getProductName())) {
					System.out.println(x);
					int orderQuantity = 0;
					orderQuantity = product.getQuantity();
					if (orderQuantity <= x.getStockAvailable()) {
						int latestQuantity = x.getStockAvailable() + orderQuantity;
						Stocks st = x;
						st.setStockAvailable(latestQuantity);

						Stocks updatedStock = storeServ.updateStocksQuantity(st);
						System.out.println("Updated latest quantity" + updatedStock);
						if (updatedStock.getStockAvailable() == latestQuantity)
							purchaseRepo.saveAndFlush(product);
					}
				}
			});

		}

	}

}
