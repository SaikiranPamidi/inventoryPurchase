package com.inventory.sales.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.sales.models.Order;
import com.inventory.sales.services.SalesServices;

@RestController
@RequestMapping("/api/v1")
public class SalesController {
	
	Logger logger = LogManager.getLogger(SalesController.class);
	
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
		logger.info(order);
		sale.placeOrder(order);
	 return "Order Placed";
	}
	
	@DeleteMapping("/deleteSalesOrder/{id}")
	public ResponseEntity<String> deletePurchasedProduct(@PathVariable("id") int id) {
		logger.info("deleting the product id :"+id);
		boolean status = sale.delectSaleProduct(id);
		if(status==true)
		  return new ResponseEntity<String>("Deleted Successfull", HttpStatus.OK);
		else
		  return new ResponseEntity<String>("No Record Found in DB", HttpStatus.BAD_REQUEST);
				 
	}
	
	
}
