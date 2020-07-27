package com.inventory.purchase.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.inventory.purchase.services.PurchaseService;
import com.inventory.purchase.conroller.PurchaseController;
import com.inventory.purchase.model.Purchase;

import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PurchaseController.class)
class PurchaseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PurchaseService purchaseService;

	Purchase mockPurchase = new Purchase();

	List<Purchase> mockList = new ArrayList<>();
	
	String mockPurchaseJson = "{\"productId\":20,\"productName\":\"Tables\",\"quantity\":100,\"stockID\":10}";

	@Test
	void getPurchasedProducts() throws Exception {

		mockPurchase.setId(1);
		mockPurchase.setProductId(20);
		mockPurchase.setProductName("Tables");
		mockPurchase.setQuantity(100);
		mockPurchase.setStockId(10);

		mockList.add(mockPurchase);

		Mockito.when(purchaseService.purchasedProducts()).thenReturn(mockList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/getPurchasedList")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[\r\n" + 
				"    {\r\n" + 
				"        \"id\": 1,\r\n" + 
				"        \"stockId\": 10,\r\n" + 
				"        \"productId\": 20,\r\n" + 
				"        \"productName\": \"Tables\",\r\n" + 
				"        \"quantity\": 100\r\n" + 
				"    }\r\n" + 
				"]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	
	@Test
	void purchaseProducts() throws Exception { 
				
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.post("http://localhost/api/v1/purchase")
				 .accept(MediaType.APPLICATION_JSON).content(mockPurchaseJson).contentType(MediaType.
				 APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		assertEquals(201, resultStatus);
		
	}

	@Test
	void deletePurchasedProduct() throws Exception { 
		
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.delete("http://localhost/api/v1/deletePurchasedProduct/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		String resultBody = result.getResponse().getContentAsString();
		assertEquals(400, resultStatus);
		assertEquals("No Record Found in DB", resultBody);
	}
	
	
	/*@Test
	void updateProductQuantity() throws Exception { 
		
		mockstock.setId(1);
		mockstock.setProductId("10");
		mockstock.setProductName("Tables");
		mockstock.setPurchased(0);
		mockstock.setStockAvailable(2011);
		mockstock.setStockID(10);
		
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.put("http://localhost/api/v1/updateStockQuantity")
				 .accept(MediaType.APPLICATION_JSON).content(mockStockJson).contentType(MediaType.
				 APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		assertEquals(200, resultStatus);
	
	}*/
}
