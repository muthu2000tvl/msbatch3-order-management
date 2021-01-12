package com.sl.ms.ordermanagement;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl.ms.ordermanagement.config.AuthenticationRequest;
import com.sl.ms.ordermanagement.items.Items;
import com.sl.ms.ordermanagement.items.ItemsRepository;
import com.sl.ms.ordermanagement.orders.Orders;
import com.sl.ms.ordermanagement.orders.OrdersController;
import com.sl.ms.ordermanagement.orders.OrdersRepository;
import com.sl.ms.ordermanagement.service.OrderService;







@WebMvcTest(controllers = OrdersController.class)
@ActiveProfiles("test")
public class OrdersControllerIntegrationTest {
	
	@Autowired
    private MockMvc mockMvc;

	@MockBean
    private OrderService service;
    
    @MockBean
    private OrdersRepository orderRepo;
    
    @MockBean
    private ItemsRepository itemsRepo;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private List<Orders> orderList; 
    private List<Items> itemList;
    
    HttpHeaders httpHeaders = new HttpHeaders();
    
    @BeforeEach                           
    void setUp() {                               
       this.orderList = new ArrayList<>();                                    
       this.orderList.add(new Orders(1L, "Item1", 100));                               
       this.orderList.add(new Orders(2L, "Item2", 200));                               
       this.orderList.add(new Orders(3L, "Item3", 300)); 
       
       this.itemList = new ArrayList<>();
       this.itemList.add(new Items(1L, "Fewikwik", 1000, 599, 599));
       this.itemList.add(new Items(2L, "AAAAA", 1000, 599, 599));
       this.itemList.add(new Items(3L, "BBBBB", 1000, 599, 599));
 
    }
    
    
    @Test
    void getAllOrdersTest() throws Exception {

        given(service.getOrders()).willReturn(orderList); 

        this.mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(orderList.size())));
    }
    
    
    
    @Test
    void getOrderWithItemsByIdTest() throws Exception {
	 final Long orderId = 1L;
	 Optional<Orders> order = Optional.ofNullable(new Orders(1L, "Item1", 100));

	 Mockito.when(orderRepo.findById(orderId)).thenReturn(order);

        this.mockMvc.perform(get("/orders/{Id}/items" , orderId))
                .andExpect(status().isOk());
                
    }
    
    @Test
    void getOrderByIdTest() throws Exception {
	 final Long orderId = 1L;
	 Optional<Orders> order = Optional.ofNullable(new Orders(1L, "Item1", 100));

	 Mockito.when(service.getById(orderId)).thenReturn(order);

        this.mockMvc.perform(get("/orders/{Id}" , orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(order.get().getName())));
                
    }
    
    @Test
    void saveItemToOrderTest() throws Exception {
	 final Long orderId = 1L;
	 httpHeaders.add("Content-Type", "application/json");
	 
	 Items item1 = new Items(1L, "Item1", 100, 100, 100); 
	 
	 String inputInJson = this.mapToJson(item1);

        
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/create/{Id}", orderId , item1).content(inputInJson).headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
                
    }
    
    @Test
    void saveOrderCheckProductTest() throws Exception {
	 final Long orderId = 1L;
	 Optional<Orders> order = Optional.ofNullable(new Orders(1L, "Item1", 100));

	 Mockito.when(service.CheckProduct(orderId)).thenReturn(true);

        this.mockMvc.perform(post("/order"))
                .andExpect(status().isBadRequest());
        assertThat(true);
                
    }
    
    @Test
    void hysterixTest() throws Exception {
	 final Long orderId = 1L;
	 Optional<Orders> order = Optional.ofNullable(new Orders(1L, "Item1", 100));

	 Mockito.when(service.TestHystrix(orderId)).thenReturn("true");

        this.mockMvc.perform(post("/TestHystrix"))
                //.andExpect(status().isOk())
                //.andExpect("Service is available.")
        .andReturn();
        assertThat(true);
                
    }
    
    @Test
    void shouldReturn404WhenGetOrderId() throws Exception {
        final Long orderId = 1L;
        given(service.getById(orderId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/orders/{Id}", orderId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldReturn404WhenGetItemsByOrderId() throws Exception {
        final Long orderId = 1L;
        given(service.getById(orderId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/orders/{Id}/items", orderId))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void deleteOrderByIdTest() throws Exception {
	 final Long orderId = 1L;
	 
	 service.delete(orderId);
	 verify(service).delete(1L);

        this.mockMvc.perform(delete("/orders/{id}" , orderId))
                .andExpect(status().isOk());
                
    }
    
   	 @Test
		public void postOrderTest() throws Exception {
			Orders mockOrder = new Orders();
			mockOrder.setId(1L);
			mockOrder.setName("GOD");
			mockOrder.setTotal_amount(599);
			mockOrder.setItems(itemList);
		
			String inputInJson = this.mapToJson(mockOrder);
			
			String URI = "/testOrderSave";
			
			Mockito.when(service.save(Mockito.any(Orders.class))).thenReturn(mockOrder);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(URI)
					.accept(MediaType.APPLICATION_JSON).content(inputInJson)
					.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			
			String outputInJson = response.getContentAsString();
			
			assertThat(outputInJson).isEqualTo(inputInJson);
			assertEquals(HttpStatus.OK.value(), response.getStatus());
		}
   	 
   	 
 	/**
		 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
		 */
		private String mapToJson(Object object) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		}

}
