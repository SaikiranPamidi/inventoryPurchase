package com.inventory.products.models;

public class StocksDTO {

	private int id;
	
   
	private int stockID;
   
	private int productId;
		
	private String productName;
	
	private int stockAvailable;
	
	private int purchased;

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

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
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
