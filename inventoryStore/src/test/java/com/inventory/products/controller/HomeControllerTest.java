package com.inventory.products.controller;

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

import com.inventory.products.models.Stocks;
import com.inventory.products.services.StockService;

import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HomeController.class)
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockService stockService;

	Stocks mockstock = new Stocks();

	List<Stocks> mockList = new ArrayList<>();
	
	String mockStockJson = "{\"stockID\":10,\"productId\":10,\"productName\":\"Tables\",\"stockAvailable\":2011,\"purchased\":0}";

	@Test
	void getStocksAvailable() throws Exception {

		mockstock.setId(1);
		mockstock.setProductId("10");
		mockstock.setProductName("Tables");
		mockstock.setPurchased(0);
		mockstock.setStockAvailable(2011);
		mockstock.setStockID(10);

		mockList.add(mockstock);

		Mockito.when(stockService.stocksAvailable()).thenReturn(mockList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/getStockList")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[\r\n" + "    {\r\n" + "        \"id\": 1,\r\n" + "        \"stockID\": 10,\r\n"
				+ "        \"productId\": \"10\",\r\n" + "        \"productName\": \"Tables\",\r\n"
				+ "        \"stockAvailable\": 2011,\r\n" + "        \"purchased\": 0\r\n" + "    }\r\n" + "]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	
	@Test
	void createProduct() throws Exception { 
				
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.post("http://localhost/api/v1/createProduct")
				 .accept(MediaType.APPLICATION_JSON).content(mockStockJson).contentType(MediaType.
				 APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		assertEquals(201, resultStatus);
		
	}

	@Test
	void updateProduct() throws Exception { 
		
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.put("http://localhost/api/v1/updateProduct")
				 .accept(MediaType.APPLICATION_JSON).content(mockStockJson).contentType(MediaType.
				 APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		assertEquals(200, resultStatus);
	}
	
	
	@Test
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
	
	}
}
