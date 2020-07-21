package com.inventory.sales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.sales.dao.OrderRepository;
import com.inventory.sales.models.Order;
import com.inventory.sales.models.Stocks;
import com.inventory.sales.services.SalesServices;

@RestController
@RequestMapping("/api/v1")
public class SalesController {
	
	@Autowired
	SalesServices sale;
	
		
	@GetMapping("")
	public String home() {
	 return "welcome to Sales Department of Inventory Application";
	}
	
	@GetMapping("/getSalesList")
	public List<Order> getStocksAvailable(){
		
		
		return sale.getAllOrdersPlaced();		
	}
	
	@PostMapping("/placeOrder")
	public String pleaceOrder(@RequestBody Order order) {
		System.out.println(order);
		sale.placeOrder(order);
	 return "Order Placed";
	}

}
