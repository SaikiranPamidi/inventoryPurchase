package com.inventory.sales.models;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,
    generator="native_order")
	@Column(name="id")
	int id;
	
	@Column(name="product_id")
	//@ManyToOne(targetEntity=Stocks.class) 
	//@JoinColumn(name="product_id")
	int productId;
	
	@Column(name="product_name")
	String productName;
	
	@Column(name="quantity_order")
	int quantity;
	
	@Column(name="ordered_date")
	Date orderedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", productId=" + productId + ", productName=" + productName + ", quantity="
				+ quantity + ", orderedDate=" + orderedDate + "]";
	}
	
	
}
