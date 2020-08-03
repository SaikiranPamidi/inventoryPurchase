package com.inventory.purchase.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurchaseConfig {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
