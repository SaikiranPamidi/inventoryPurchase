package com.inventory.products.services;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.inventory.products.dao.StockRepository;
import com.inventory.products.exception.ProductNotFoundException;
import com.inventory.products.models.Stocks;
import com.inventory.products.models.StocksDTO;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class StockServiceTest {

	@Mock
	private StockRepository stockRepo;

	@InjectMocks
	private StockService stockService;
	

	private ModelMapper modelMapper=new ModelMapper();
	
	@Test
	void testingModelMapper() throws Exception{
		
		Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(10);
		
		StocksDTO stockDto = new StocksDTO();
		stockDto.setId(1);
		stockDto.setProductId(10);
		stockDto.setProductName("Tables");
		stockDto.setPurchased(0);
		stockDto.setStockAvailable(2011);
		stockDto.setStockID(10);
		
		Stocks result= modelMapper.map(stockDto, Stocks.class);
		assertEquals(stock.getId(), result.getId());
		
	}
	
	
	
	

	@Test
	void createStocks() throws Exception{
		final Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(10);
		
		Mockito.when(stockRepo.saveAndFlush(stock)).thenReturn(stock);
		
		String excepted="added to store";
		assertEquals(excepted, stockService.createStocks(stock));
	}
	
	@Test
	void stocksAvailable() throws Exception {

		final Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(10);

		List<Stocks> expResult = new ArrayList<>();

		expResult.add(stock);

		Mockito.when(stockRepo.findAll()).thenReturn(expResult);

		List<Stocks> result = stockService.stocksAvailable();

		assertEquals(expResult, result);

	}

	@Test
	void updateStocks() throws Exception {
		final Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(10);

		Mockito.when(stockRepo.getOne(stock.getId())).thenReturn(stock);

		Stocks result = stockService.updateStocks(stock);

		assertEquals(stock, result);
	}

	@Test
	void updateQuantityStocks() throws Exception {
		final Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(10);

		Mockito.when(stockRepo.findById(stock.getId())).thenReturn(Optional.of(stock));

		Stocks result = stockService.updateQuantityStocks(stock);
		assertEquals(stock, result);
	
	}

	
}
