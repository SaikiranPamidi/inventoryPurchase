package com.inventory.sales.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.inventory.sales.models.Stocks;
import com.inventory.sales.dao.OrderRepository;
import com.inventory.sales.models.Order;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class SalesServicesTest {

	@Mock
	private OrderRepository orderRepo;

	@InjectMocks
	private SalesServices salesService;

	@Mock
	private StoreService storeService;

	@Test
	void getAllOrdersPlaced() throws Exception {

		final Order order = new Order();
		order.setId(1);
		order.setProductId(10);
		order.setProductName("Tables");
		order.setQuantity(10);

		List<Order> expResult = new ArrayList<>();
		expResult.add(order);

		final Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId("10");
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(10);

		List<Stocks> stockList = new ArrayList<>();
		stockList.add(stock);

		Mockito.when(storeService.getStocksAvailable()).thenReturn(stockList);
		Mockito.when(orderRepo.findAll()).thenReturn(expResult);

		List<Order> result = salesService.getAllOrdersPlaced();

		assertEquals(expResult, result);
	}

	@Test
	void delectSaleProduct() throws Exception {
		
		final Order order = new Order();
		order.setId(1);
		order.setProductId(10);
		order.setProductName("Tables");
		order.setQuantity(10);
		
		Mockito.when(orderRepo.findById(1)).thenReturn(Optional.of(order));

		boolean result = salesService.delectSaleProduct(1); 
		assertEquals(true, result); 
	}

	/*
	 * @Test void placeOrder() {
	 * 
	 * final Order order = new Order(); order.setId(1); order.setProductId(10);
	 * order.setProductName("Tables"); order.setQuantity(10);
	 * 
	 * final Stocks stock = new Stocks(); stock.setId(1); stock.setProductId("10");
	 * stock.setProductName("Tables"); stock.setPurchased(0);
	 * stock.setStockAvailable(2011); stock.setStockID(10);
	 * 
	 * List<Stocks> stockList = new ArrayList<>(); stockList.add(stock);
	 * 
	 * final Stocks stockUpdated = new Stocks(); stockUpdated.setId(1);
	 * stockUpdated.setProductId("10"); stockUpdated.setProductName("Tables");
	 * stockUpdated.setPurchased(10); stockUpdated.setStockAvailable(2009);
	 * stockUpdated.setStockID(10);
	 * 
	 * List<Order> expResult = new ArrayList<>(); expResult.add(order);
	 * 
	 * Mockito.when(storeService.getStocksAvailable()).thenReturn(stockList);
	 * Mockito.when(storeService.updateStocksQuantity(stock)).thenReturn(
	 * stockUpdated); Mockito.when(orderRepo.saveAndFlush(order)).thenReturn(order);
	 * 
	 * // assertEquals(expResult, result);
	 * 
	 * List<Order> result = salesService.placeOrder(order);
	 * 
	 * assertEquals(expResult, result); }
	 * 
	 * @Test void delectSaleProduct() { boolean result =
	 * salesService.delectSaleProduct(1); assertEquals(false, result); }
	 */

}
