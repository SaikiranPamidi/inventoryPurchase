package com.inventory.sales.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inventory.sales.dao.OrderRepository;
import com.inventory.sales.models.Order;
import com.inventory.sales.models.OrderDTO;
import com.inventory.sales.models.Stocks;

@Component
public class SalesServices {

	Logger logger = LogManager.getLogger(SalesServices.class);
	
	@Autowired
	OrderRepository orderRepo;

	@Autowired
	private StoreService store;
	
	@Autowired
	ModelMapper modelMapper;

	public List<Order> getAllOrdersPlaced() {
		List<Stocks> stockList = store.getStocksAvailable();
		stockList.forEach(x -> logger.info("Stock Data : {}" , x));
		return orderRepo.findAll();
	}

	public List<Order> placeOrder(Order order) {

		List<Order> orderedProduct = new ArrayList<>();
		
		logger.info("Order Data : {}" , order);
		List<Stocks> stockList = store.getStocksAvailable();
		stockList.forEach(x -> {
			logger.info("Stock Data : {}" , x);
			if(x.getProductName().equalsIgnoreCase(order.getProductName()))
			{
				int orderQuantity=0;
				orderQuantity=order.getQuantity();
				if(orderQuantity<=x.getStockAvailable())
				{
					int latestQuantity=x.getStockAvailable()-orderQuantity;
					Stocks st=x;
					st.setStockAvailable(latestQuantity);
					st.setPurchased(st.getPurchased()+orderQuantity);
					Stocks updatedStock=store.updateStocksQuantity(st);
					logger.info("Updated latest quantity {}", updatedStock);
					if(updatedStock.getStockAvailable()==latestQuantity) {
						Order ordered =orderRepo.saveAndFlush(order);
						orderedProduct.add(ordered);
						logger.info("Order Product update to DB");
					}
				}
			}
		});
		
		return orderedProduct;		
	}

	public boolean delectSaleProduct(int id) {
		Order order=orderRepo.findById(id).orElseThrow();
		if(order!=null) {
			orderRepo.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

	public Order convertToEntity(OrderDTO orderDto) {
		logger.info(orderDto);
		return modelMapper.map(orderDto, Order.class);
	}

}
