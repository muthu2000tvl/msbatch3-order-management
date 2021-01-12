package com.sl.ms.ordermanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sl.ms.ordermanagement.orders.Orders;
import com.sl.ms.ordermanagement.orders.OrdersController;
import com.sl.ms.ordermanagement.orders.OrdersRepository;
import com.sl.ms.ordermanagement.service.OrderService;





@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
public class OrderManagementApplicationTests {
	

	@Test
	void contextLoads() {
	}
	
	@InjectMocks
	private OrderService ordser;
	
	@Mock
	private OrdersRepository ordrepo;
	
	private static final Logger log = LoggerFactory.getLogger(OrderManagementApplicationTests.class);
	
	@Test
	public void getOrdersTest() {
		when(ordrepo.findAll()).thenReturn(Stream.of(new Orders(1L, "Item1", 499), new Orders(2L, "Item 2", 599)).
				collect(Collectors.toList()));
		
		assertEquals(2, ordser.getOrders().size());
	}
	
	
	
	 @Test
	    void getById(){
	        final Long id = 1L;
	        final Orders orders = new Orders(1L, "Muthu",100);

	        given(ordrepo.findById(id)).willReturn(Optional.of(orders));

	        final Optional<Orders> expected  =ordser.getById(id);

	        assertThat(expected).isNotNull();

	    }
	
	 @Test
	    public void saveOrdersTest()
	    {
	        Orders or = new Orders(2L,"Muthu",500);
	        when(ordser.save(or)).thenReturn(or);	        
	        assertEquals(or, ordser.save(or));
	    }
	 
//	 @Test
//	    public void checkProductTest()
//	    {
//	        boolean check = true;
//	        when(ordser.CheckProduct(1L)).thenReturn(true);	        
//	        assertEquals(check, ordser.CheckProduct(1L));
//	    }
	
	 @Test
	    public void getOrdersByIdTest()
	    {
	        Orders or = new Orders(2L,"Muthu",500);
	        
	        when(ordser.getByIdTest(2L)).thenReturn(Optional.of(or));        
	        assertEquals(or, ordser.getByIdTest(2L).get());
	    }
	 
//	 @Test
//	    public void hysterixFallbackTest()
//	    {
//	        String message = "Looks like service unavailable. Please try later";
//	        
//	        when(ordser.TestHystrixFallback(2L)).thenReturn(message); 
//	        final String expected  =ordser.TestHystrixFallback(1L);
//	        assertThat(expected).isNotNull();
//	        assertEquals(message, expected);
//	    }
	 
	 
	 
	 @Test
	    void shouldBeDelete() {
	        final Long userId=1L;

	        ordser.delete(userId);
	        ordser.delete(userId);

	        verify(ordrepo, times(2)).deleteById(userId);
	    }


}
