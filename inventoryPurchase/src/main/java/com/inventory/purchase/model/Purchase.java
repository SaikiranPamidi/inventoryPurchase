package com.inventory.purchase.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="purchase_stock")
public class Purchase implements Serializable{
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,
    generator="native_purchase")	
    @Column(name="id")
	int id;
	
	@Column(name="stock_id")
	int stockId;
	
	@Column(name="product_id")
	//@ManyToOne(targetEntity=Stocks.class)  
	int productId;
	
	@Column(name="product_name")
	String productName;
	
	@Column(name="quantity")
	int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", stockId=" + stockId + ", productId=" + productId + ", productName="
				+ productName + ", quantity=" + quantity + "]";
	}
	
}
