package com.inventory.purchase.exception;

public class ProductNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(int id) {

        super(String.format("Product not found %d ",  id));
    }
	
}
