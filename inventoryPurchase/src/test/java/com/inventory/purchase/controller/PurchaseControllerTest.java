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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.inventory.purchase.conroller.PurchaseController;
import com.inventory.purchase.exception.NoDataFoundException;
import com.inventory.purchase.exception.ProductNotFoundException;
import com.inventory.purchase.model.Purchase;
import com.inventory.purchase.model.PurchaseDTO;
import com.inventory.purchase.services.PurchaseService;
import com.sun.jersey.api.client.ClientResponse.Status;

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
	@WithMockUser(username = "saikiran", password = "saikiran", roles = "ADMIN")
	void home() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost/api/v1/");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String resultBody = result.getResponse().getContentAsString();
		assertEquals("welcome to Invenotry Purchase Department", resultBody);
	}

	@Test
	@WithMockUser(username = "saikiran", password = "saikiran", roles = "ADMIN")
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

		String expected = "[\r\n" + "    {\r\n" + "        \"id\": 1,\r\n" + "        \"stockId\": 10,\r\n"
				+ "        \"productId\": 20,\r\n" + "        \"productName\": \"Tables\",\r\n"
				+ "        \"quantity\": 100\r\n" + "    }\r\n" + "]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	@WithMockUser(username = "saikiran", password = "saikiran", roles = "ADMIN")
	void purchaseProducts() throws Exception {

		mockPurchase.setId(1);
		mockPurchase.setProductId(20);
		mockPurchase.setProductName("Tables");
		mockPurchase.setQuantity(100);
		mockPurchase.setStockId(10);

		PurchaseDTO mockPurchaseDto = new PurchaseDTO();

		mockPurchaseDto.setId(1);
		mockPurchaseDto.setProductId(20);
		mockPurchaseDto.setProductName("Tables");
		mockPurchaseDto.setQuantity(100);
		mockPurchaseDto.setStockId(10);

		Mockito.when(purchaseService.convertToEntity(mockPurchaseDto)).thenReturn(mockPurchase);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost/api/v1/purchase")
				.accept(MediaType.APPLICATION_JSON).content(mockPurchaseJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		assertEquals(201, resultStatus);

	}

	@Test
	@WithMockUser(username = "saikiran", password = "saikiran", roles = "ADMIN")
	void deletePurchasedProduct() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("http://localhost/api/v1/deletePurchasedProduct/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		String resultBody = result.getResponse().getContentAsString();
		assertEquals(400, resultStatus);
		assertEquals("No Record Found in DB", resultBody);
	}

	@Test
	@WithMockUser(username = "saikiran", password = "saikiran", roles = "ADMIN")
	void deletePurchasedProductCase2() throws Exception {

		Mockito.when(purchaseService.delectPurchaseProducts(1)).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("http://localhost/api/v1/deletePurchasedProduct/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		String resultBody = result.getResponse().getContentAsString();
		assertEquals(200, resultStatus);
		assertEquals("Deleted Successfull", resultBody);
	}

	@Test
	@WithMockUser(username = "saikiran", password = "saikiran", roles = "ADMIN")
	void deletePurchasedProductCase3() throws Exception {

		Mockito.when(purchaseService.delectPurchaseProducts(1)).thenThrow(new ProductNotFoundException(1));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("http://localhost/api/v1/deletePurchasedProduct/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		assertEquals(404, resultStatus);
	}

	@Test
	@WithMockUser(username = "saikiran", password = "saikiran", roles = "ADMIN")
	void getProductsPurchasedCase2() throws Exception {

		Mockito.when(purchaseService.purchasedProducts()).thenThrow(new NoDataFoundException());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/getPurchasedList")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(404, result.getResponse().getStatus());
	}

}
