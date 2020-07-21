package com.inventory.sales.models;

import java.io.Serializable;


public class Stocks implements Serializable {
		
	int id;
	
    int stockID;
    	
	String productId;
	
	String productName;
	
	int stockAvailable;
	
	int purchased;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStockID() {
		return stockID;
	}

	public void setStockID(int stockID) {
		this.stockID = stockID;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getStockAvailable() {
		return stockAvailable;
	}

	public void setStockAvailable(int stockAvailable) {
		this.stockAvailable = stockAvailable;
	}

	public int getPurchased() {
		return purchased;
	}

	public void setPurchased(int purchased) {
		this.purchased = purchased;
	}

	@Override
	public String toString() {
		return "Stocks [id=" + id + ", stockID=" + stockID + ", productId=" + productId + ", productName=" + productName
				+ ", stockAvailable=" + stockAvailable + ", purchased=" + purchased + "]";
	}
	
	
}
