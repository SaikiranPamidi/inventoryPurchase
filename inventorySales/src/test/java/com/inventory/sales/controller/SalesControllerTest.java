package com.inventory.sales.controller;

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

import com.inventory.sales.models.Order;
import com.inventory.sales.services.SalesServices;

import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SalesController.class)
class SalesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SalesServices saleService;

	Order mockOrder = new Order();

	List<Order> mockList = new ArrayList<>();
	
	String mockOrderJson = "{\"productId\":20,\"productName\":\"Tables\",\"quantity\":100}";
	
	@Test
	void home() throws Exception{
		
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.get("http://localhost/api/v1/");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String resultBody = result.getResponse().getContentAsString();
		assertEquals("welcome to Sales Department of Inventory Application", resultBody);
	}

	@Test
	void getStocksAvailable() throws Exception {

		mockOrder.setId(1);
		mockOrder.setProductId(20);
		mockOrder.setProductName("Tables");
		mockOrder.setQuantity(100);
		
		mockList.add(mockOrder);

		Mockito.when(saleService.getAllOrdersPlaced()).thenReturn(mockList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/getSalesList")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[\r\n" + 
				"    {\r\n" + 
				"        \"id\": 1,\r\n" + 
				"        \"productId\": 20,\r\n" + 
				"        \"productName\": \"Tables\",\r\n" + 
				"        \"quantity\": 100\r\n" + 
				"    }\r\n" + 
				"]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	
	@Test
	void placeOrder() throws Exception { 
				
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.post("http://localhost/api/v1/placeOrder")
				 .accept(MediaType.APPLICATION_JSON).content(mockOrderJson).contentType(MediaType.
				 APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		assertEquals(400, resultStatus);
		
	}

	@Test
	void deleteOrderedProduct() throws Exception { 
		
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.delete("http://localhost/api/v1/deleteSalesOrder/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		String resultBody = result.getResponse().getContentAsString();
		assertEquals(400, resultStatus);
		assertEquals("No Record Found in DB", resultBody);	
		
	}
	
	@Test
	void deleteOrderedProductTest() throws Exception { 
		
		RequestBuilder requestBuilder =
				 MockMvcRequestBuilders.delete("http://localhost/api/v1/deleteSalesOrder/1");
		
		Mockito.when(saleService.delectSaleProduct(1)).thenReturn(true);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int resultStatus = result.getResponse().getStatus();
		String resultBody = result.getResponse().getContentAsString();
		assertEquals(200, resultStatus);
		assertEquals("Deleted Successfull", resultBody);	
		
	}
	
}
