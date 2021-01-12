//package com.sl.ms.ordermanagement;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.sl.ms.ordermanagement.items.Items;
//import com.sl.ms.ordermanagement.items.ItemsRepository;
//import com.sl.ms.ordermanagement.orders.Orders;
//import com.sl.ms.ordermanagement.orders.OrdersRepository;
//
//
//@RunWith(SpringRunner.class)
//public class OrdersRepositoryIntegrationTest {
//	
//	//@Autowired
//    //private TestEntityManager entityManager;
//
//    @MockBean
//    private OrdersRepository orderRepo;
//    
//    @MockBean
//    private ItemsRepository itemsRepo;
//    
//    private List<Items> itemList;
//    
//    @BeforeEach                           
//    void setUp() {                               
//      
//       this.itemList = new ArrayList<>();
//       this.itemList.add(new Items(1L, "Fewikwik", 1000, 599, 599));
//       this.itemList.add(new Items(2L, "AAAAA", 1000, 599, 599));
//       this.itemList.add(new Items(3L, "BBBBB", 1000, 599, 599));
// 
//    }
//
//    @Test
//    public void saveOrdersTest()
//    {
//        Orders or = new Orders(2L,"Muthu",500);
//        when(orderRepo.save(or)).thenReturn(or);	        
//        assertEquals(or, orderRepo.save(or));
//    }
//    
//}
