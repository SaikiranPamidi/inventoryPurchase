package com.inventory.products.models;

import lombok.Data;

@Data 
public class StocksDTO {

	private int id;
	
   
	private int stockID;
   
	private int productId;
		
	private String productName;
	
	private int stockAvailable;
	
	private int purchased;

	
}
