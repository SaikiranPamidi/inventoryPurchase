package com.inventory.purchase.conroller;

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

import com.inventory.purchase.model.Purchase;
import com.inventory.purchase.model.PurchaseDTO;
import com.inventory.purchase.services.PurchaseService;

@RestController
@RequestMapping("/api/v1")
public class PurchaseController {
	
	Logger logger = LogManager.getLogger(PurchaseController.class);
	
	@Autowired
	PurchaseService ps;
	
	@GetMapping("")
	public String home() {
	 return "welcome to Invenotry Purchase Department";
	}
	
	@GetMapping("/getPurchasedList")
	public List<Purchase> getPurchasedProducts(){
		
		return ps.purchasedProducts();		
	}
	
	@PostMapping("/purchase")
	public ResponseEntity<String> purchaseProducts(@RequestBody PurchaseDTO productDto) {
		logger.info(productDto);
		Purchase product = ps.convertToEntity(productDto);
		ps.purchaseProducts(product);
	 return new ResponseEntity<>("Product got Added to Stock", HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/deletePurchasedProduct/{id}")
	public ResponseEntity<String> deletePurchasedProduct(@PathVariable("id") int id) {
		logger.info("deleting the product id : %d", id);
		boolean status = ps.delectPurchaseProducts(id);
		if(status)
		  return new ResponseEntity<>("Deleted Successfull", HttpStatus.OK);
		else
		  return new ResponseEntity<>("No Record Found in DB", HttpStatus.BAD_REQUEST);
				 
	}
}
