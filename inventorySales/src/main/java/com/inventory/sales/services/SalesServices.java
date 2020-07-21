package com.inventory.sales.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.inventory.sales.dao.OrderRepository;
import com.inventory.sales.models.Order;
import com.inventory.sales.models.Stocks;

@Component
public class SalesServices {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	private StoreService store;

	public List<Order> getAllOrdersPlaced() {
		// TODO Auto-generated method stub
		List<Stocks> stockList = store.getStocksAvailable();
		stockList.forEach(x -> System.out.println("Stock Data : " + x));

		List<Order> ordersList = orderRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return ordersList;
	}

	public void placeOrder(Order order) {

		System.out.println(order);
		List<Stocks> stockList = store.getStocksAvailable();
		stockList.forEach(x -> {
			System.out.println("Stock Data : " + x);
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
					System.out.println("Updated latest quantity");
					if(updatedStock.getStockAvailable()==latestQuantity)
						orderRepo.saveAndFlush(order);
				}
			}
		});
		
	}

}
