package com.inventory.purchase.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.purchase.exception.ProductNotFoundException;
import com.inventory.purchase.dao.PurchaseRepo;
import com.inventory.purchase.model.Purchase;
import com.inventory.purchase.model.PurchaseDTO;
import com.inventory.purchase.model.Stocks;

@Component
public class PurchaseService {

	Logger logger = LogManager.getLogger(PurchaseService.class);

	@Autowired
	PurchaseRepo purchaseRepo;

	@Autowired
	private InvStoreService storeServ;

	@Autowired
	ModelMapper modelMapper;

	public List<Purchase> purchasedProducts() {

		return purchaseRepo.findAll();

	}

	public boolean purchaseProducts(Purchase product) {
		logger.info(product);
		boolean status = false;
		List<Stocks> stockList = storeServ.getStocksAvailable();

		if (stockList.isEmpty()) {
			status = addingProductToStore(product);
			return status;

		} else {
			logger.info("Products available comparing with existing product");

			List<Stocks> lt = compareWithProductName(stockList, product);

			if (lt.isEmpty()) {
				status = addingProductToStore(product);
				return status;
			} else {
				for (int i = 0; i < lt.size(); i++) {
					status = true;
				}
				return status;
			}
		}

	}

	public boolean addingProductToStore(Purchase product) {
		logger.info("existing product doesn't match with new purchase product");
		Stocks st = new Stocks();
		st.setProductId(product.getProductId());
		st.setProductName(product.getProductName());
		st.setStockAvailable(product.getQuantity());
		st.setStockID(product.getStockId());
		logger.info(st);
		String entity = storeServ.createProduct(st);
		if (entity.equalsIgnoreCase("Purchased Stocks to Store Successfull") ) {
			purchaseRepo.saveAndFlush(product);
			return true;
		} else {
			return false;
		}
	}

	public List<Stocks> compareWithProductName(List<Stocks> stockList, Purchase product) {

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
				int upQuantity=updatedStock.getStockAvailable();
				if ( upQuantity== latestQuantity) {
					purchaseRepo.saveAndFlush(product);
				}

				lt.add(st);
			}
		});
		return lt;
	}

	public boolean delectPurchaseProducts(int id) {

		Purchase product = purchaseRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		if (product != null) {
			purchaseRepo.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

	public Purchase convertToEntity(PurchaseDTO productDto) {
		logger.info(productDto);
		return modelMapper.map(productDto, Purchase.class);
	}

}
