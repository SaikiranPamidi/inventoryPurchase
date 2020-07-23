package com.inventory.sales.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.inventory.sales.dao.OrderRepository;
import com.inventory.sales.models.Order;
import com.inventory.sales.models.Stocks;

@Component
public class SalesServices {

	Logger logger = LogManager.getLogger(SalesServices.class);
	
	@Autowired
	OrderRepository orderRepo;

	@Autowired
	private StoreService store;

	public List<Order> getAllOrdersPlaced() {
		// TODO Auto-generated method stub
		List<Stocks> stockList = store.getStocksAvailable();
		stockList.forEach(x -> logger.info("Stock Data : " + x));

		List<Order> ordersList = orderRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return ordersList;
	}

	public void placeOrder(Order order) {

		System.out.println(order);
		List<Stocks> stockList = store.getStocksAvailable();
		stockList.forEach(x -> {
			logger.info("Stock Data : " + x);
			if(x.getProductName().equalsIgnoreCase(order.getProductName()))
			{
				System.out.println(x);
				int orderQuantity=0;
				orderQuantity=order.getQuantity();
				if(orderQuantity<=x.getStockAvailable())
				{
					int latestQuantity=x.getStockAvailable()-orderQuantity;
					Stocks st=x;
					st.setStockAvailable(latestQuantity);
					st.setPurchased(st.getPurchased()+orderQuantity);
					Stocks updatedStock=store.updateStocksQuantity(st);
					logger.info("Updated latest quantity");
					if(updatedStock.getStockAvailable()==latestQuantity)
						orderRepo.saveAndFlush(order);
				}
			}
		});
		
	}

	public boolean delectSaleProduct(int id) {
		Order order =orderRepo.getOne(id);
		if(order!=null) {
			orderRepo.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

}
