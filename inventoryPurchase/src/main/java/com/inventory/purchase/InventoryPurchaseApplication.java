package com.inventory.purchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InventoryPurchaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryPurchaseApplication.class, args);
	}

}
