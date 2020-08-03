package com.inventory.purchase.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.inventory.purchase.dao.PurchaseRepo;
import com.inventory.purchase.model.Purchase;
import com.inventory.purchase.model.Stocks;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

	@Mock
	PurchaseRepo purchaseRepo;

	@InjectMocks
	private PurchaseService purchaseService;

	@Mock
	InvStoreService storeService;

	@Test
	void purchasedProducts() throws Exception {

		final Purchase purchase = new Purchase();
		purchase.setId(1);
		purchase.setProductId(10);
		purchase.setProductName("Tables");
		purchase.setQuantity(1000);
		purchase.setStockId(100);

		List<Purchase> expResult = new ArrayList<>();

		expResult.add(purchase);

		Mockito.when(purchaseRepo.findAll()).thenReturn(expResult);

		List<Purchase> result = purchaseService.purchasedProducts();

		assertEquals(expResult, result);
	}

	@Test
	void deletePurchasedProduct() throws Exception {

		Purchase purchase = new Purchase();
		purchase.setId(1);
		purchase.setProductId(10);
		purchase.setProductName("Tables");
		purchase.setQuantity(1000);
		purchase.setStockId(100);

		Mockito.when(purchaseRepo.findById(1)).thenReturn(Optional.of(purchase));
		// verify(orderRepo).deleteById(any());;

		boolean result = purchaseService.delectPurchaseProducts(1);
		assertEquals(true, result);
	}

	@Test
	void compareWithProductsTest() throws Exception {

		Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(100);

		List<Stocks> stockList = new ArrayList<>();
		stockList.add(stock);

		Purchase purchase = new Purchase();
		purchase.setId(1);
		purchase.setProductId(10);
		purchase.setProductName("Tables");
		purchase.setQuantity(1000);
		purchase.setStockId(100);

		List<Stocks> excepted = new ArrayList<>();
		Stocks stockExcepted = new Stocks();
		stockExcepted.setId(1);
		stockExcepted.setProductId(10);
		stockExcepted.setProductName("Tables");
		stockExcepted.setPurchased(0);
		stockExcepted.setStockAvailable(3011);
		stockExcepted.setStockID(100);
		excepted.add(stockExcepted);

		Mockito.when(storeService.updateStocksQuantity(stock)).thenReturn(stockExcepted);
		Mockito.when(purchaseRepo.saveAndFlush(purchase)).thenReturn(purchase);

		List<Stocks> result = purchaseService.compareWithProductName(stockList, purchase);
		assertEquals(excepted.toString(), result.toString());

	}

	@Test
	void compareWithProductsTestCase2() throws Exception {

		Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Chairs");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(100);

		List<Stocks> stockList = new ArrayList<>();
		stockList.add(stock);

		Purchase purchase = new Purchase();
		purchase.setId(1);
		purchase.setProductId(10);
		purchase.setProductName("Tables");
		purchase.setQuantity(1000);
		purchase.setStockId(100);

		List<Stocks> excepted = new ArrayList<>();

		List<Stocks> result = purchaseService.compareWithProductName(stockList, purchase);
		assertEquals(excepted.toString(), result.toString());

	}

	@Test
	void purchaseProducts() throws Exception {

		Stocks stock = new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Tables");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(100);

		List<Stocks> stockList = new ArrayList<>();
		stockList.add(stock);

		Purchase purchase = new Purchase();
		purchase.setId(1);
		purchase.setProductId(10);
		purchase.setProductName("Tables");
		purchase.setQuantity(1000);
		purchase.setStockId(100);

		List<Stocks> excepted = new ArrayList<>();
		Stocks stockExcepted = new Stocks();
		stockExcepted.setId(1);
		stockExcepted.setProductId(10);
		stockExcepted.setProductName("Tables");
		stockExcepted.setPurchased(0);
		stockExcepted.setStockAvailable(3011);
		stockExcepted.setStockID(100);
		excepted.add(stockExcepted);
		
		Mockito.when(storeService.getStocksAvailable()).thenReturn(stockList);
		Mockito.when(storeService.updateStocksQuantity(stock)).thenReturn(stockExcepted);
		Mockito.when(purchaseRepo.saveAndFlush(purchase)).thenReturn(purchase);

		//Mockito.when(purchaseService.compareWithProductName(stockList, purchase));
		boolean status = purchaseService.purchaseProducts(purchase);
		assertEquals(true, status);
		
	}
	
	
	/*
	 * @Test void purchaseProductsCase1() throws Exception {
	 * 
	 * Stocks stock = new Stocks(); stock.setId(1); stock.setProductId(10);
	 * stock.setProductName("Chairs"); stock.setPurchased(0);
	 * stock.setStockAvailable(2011); stock.setStockID(100);
	 * 
	 * List<Stocks> stockList = new ArrayList<>(); stockList.add(stock);
	 * 
	 * Purchase purchase = new Purchase(); purchase.setId(1);
	 * purchase.setProductId(10); purchase.setProductName("Tables");
	 * purchase.setQuantity(1000); purchase.setStockId(100);
	 * 
	 * List<Stocks> excepted = new ArrayList<>(); Stocks stockExcepted = new
	 * Stocks(); stockExcepted.setProductId(10);
	 * stockExcepted.setProductName("Tables"); stockExcepted.setPurchased(0);
	 * stockExcepted.setStockAvailable(1000); stockExcepted.setStockID(100);
	 * excepted.add(stockExcepted);
	 * 
	 * Mockito.when(storeService.getStocksAvailable()).thenReturn(stockList);
	 * Mockito.when(storeService.createProduct(stockExcepted)).
	 * thenReturn("Purchased Stocks to Store Successfull");
	 * Mockito.when(purchaseRepo.saveAndFlush(purchase)).thenReturn(purchase);
	 * 
	 * //Mockito.when(purchaseService.compareWithProductName(stockList, purchase));
	 * boolean status = purchaseService.purchaseProducts(purchase);
	 * assertEquals(true, status);
	 * 
	 * }
	 */
	
	@Test
	void purchaseProductsCase2() throws Exception {
		Stocks stock=new Stocks();
		stock.setId(1);
		stock.setProductId(10);
		stock.setProductName("Chairs");
		stock.setPurchased(0);
		stock.setStockAvailable(2011);
		stock.setStockID(100);
		
		assertEquals(10, stock.getProductId());
		assertEquals(1, stock.getId());
		assertEquals(0, stock.getPurchased());
		assertEquals(2011, stock.getStockAvailable());
		assertEquals(100, stock.getStockID());
		
	}

}
