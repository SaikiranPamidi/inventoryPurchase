package com.inventory.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class InventorySalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventorySalesApplication.class, args);
	}

}
