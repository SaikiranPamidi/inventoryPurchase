package com.inventory.purchase.conroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.purchase.model.Purchase;
import com.inventory.purchase.services.PurchaseService;

@RestController
@RequestMapping("/api/v1")
public class PurchaseController {
	
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
	public String purchaseProducts(@RequestBody Purchase product) {
		System.out.println(product);
		ps.purchaseProducts(product);
	 return "Product got Added to Stock";
	}

}
