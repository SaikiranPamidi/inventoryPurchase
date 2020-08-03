package com.inventory.products.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "inventory_stocks")
public class Stocks implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,
    generator="native_store")	
    @Column(name="id")
	int id;
	
    @Column(name="stock_id")
	int stockID;
    	
	@Column(name="product_id")
	int productId;
	
	@Column(name="product_name")
	String productName;
	
	@Column(name="stock_available")
	int stockAvailable;
	
	@Column(name="purchased")
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
