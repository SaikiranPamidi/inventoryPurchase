package com.inventory.purchase.model;

import lombok.Data;

@Data
public class PurchaseDTO {
	
	int id;
	
	int stockId;
	
	int productId;
	
	String productName;
	
	int quantity;

	
}
