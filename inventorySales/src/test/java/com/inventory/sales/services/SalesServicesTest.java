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
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.inventory.sales.models.Stocks;
import com.inventory.sales.dao.OrderRepository;
import com.inventory.sales.models.Order;
import com.inventory.sales.models.OrderDTO;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class SalesServicesTest {

	@Mock
	private OrderRepository orderRepo;

	@InjectMocks
	private SalesServices salesService;

	@Mock
	private StoreService storeService;

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	void testingModelMapper() throws Exception {

		Order order = new Order();
		order.setId(1);
		order.setProductId(10);
		order.setProductName("Tables");
		order.setQuantity(100);

		OrderDTO orderDto = new OrderDTO();
		orderDto.setId(1);
		orderDto.setProductId(10);
		orderDto.setProductName("Tables");
		orderDto.setQuantity(100);

		Order result = modelMapper.map(orderDto, Order.class);
		assertEquals(order.toString(), result.toString());
		assertEquals(orderDto.toString(), orderDto.toString());

	}

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
		stock.setProductId(10);
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

	@Test
	void purchaseProduct() throws Exception {
		Order order = new Order();
		order.setId(1);
		order.setProductId(10);
		order.setProductName("Tables");
		order.setQuantity(10);

		List<Order> orderList=new ArrayList<>();
		orderList.add(order);
		
		Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(10);

		List<Stocks> stockList = new ArrayList<>();

		stockList.add(stock);
		
		Stocks upDatedstock = new Stocks();
		upDatedstock.setId(1);
		upDatedstock.setProductId(10);
		upDatedstock.setProductName("Tables");
		upDatedstock.setPurchased(0);
		upDatedstock.setStockAvailable(2001);
		upDatedstock.setStockID(10);
		
		Mockito.when(storeService.getStocksAvailable()).thenReturn(stockList);
		Mockito.when(storeService.updateStocksQuantity(stock)).thenReturn(upDatedstock);
		Mockito.when(orderRepo.saveAndFlush(order)).thenReturn(order);
		List<Order> result= salesService.placeOrder(order);
		assertEquals(orderList, result);
	}

}
