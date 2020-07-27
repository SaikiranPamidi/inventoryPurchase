package com.inventory.products.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inventory.products.dao.StockRepository;
import com.inventory.products.models.Stocks;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class StockServiceTest {

	@Mock
    private StockRepository stockRepo;

	
	  @InjectMocks 
	  private StockService stockService;
	 

	/*
	 * @Test void createStocks(Stocks st) {
	 * 
	 * final Stocks stock=new Stocks(); stock.setId(1); stock.setProductId("10");
	 * stock.setProductName("Tables"); stock.setPurchased(0);
	 * stock.setStockAvailable(2011); stock.setStockID(10);
	 * 
	 * when(stockRepo.save(stock)).thenReturn(stock);
	 * 
	 * assertEquals(expected, actual);
	 * 
	 * }
	 */

}
