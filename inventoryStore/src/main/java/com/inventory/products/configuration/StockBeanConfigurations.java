package com.inventory.products.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockBeanConfigurations {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
