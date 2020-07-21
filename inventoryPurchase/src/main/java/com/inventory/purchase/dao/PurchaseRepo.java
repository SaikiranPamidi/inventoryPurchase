package com.inventory.purchase.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.purchase.model.Purchase;

public interface PurchaseRepo extends JpaRepository<Purchase, Integer>{

}
