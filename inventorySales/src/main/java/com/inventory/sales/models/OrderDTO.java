package com.inventory.sales.models;

import java.sql.Date;

import lombok.Data;

@Data
public class OrderDTO {

	int id;
		
	int productId;
	
	String productName;
	
	int quantity;
	
	Date orderedDate;
}
