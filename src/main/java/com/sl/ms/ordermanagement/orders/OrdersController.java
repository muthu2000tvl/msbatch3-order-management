package com.sl.ms.ordermanagement.orders;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sl.ms.ordermanagement.exceptions.OrderNotFoundException;

import com.sl.ms.ordermanagement.items.Items;
import com.sl.ms.ordermanagement.items.ItemsRepository;
import com.sl.ms.ordermanagement.orders.Orders;
import com.sl.ms.ordermanagement.service.OrderService;




@RestController
public class OrdersController {
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	OrdersRepository repo;
	
	@Autowired
	ItemsRepository itemsrepo;
	
	@Autowired
	OrderService orderservice;
	
	private static final Logger log = LoggerFactory.getLogger(OrdersController.class.getName());
	
	/*
	 * To display the Orders from repo
	 */
	
	@GetMapping("/orders")
	public List<Orders> getOrders(){
		log.info("Entering getOrders() Controller");	
		
		List<Orders> orders = (List<Orders>) orderservice.getOrders();
		return orders;
	}
	
	/*
	 * To display the Orders by ID from repo
	 */
	
	@RequestMapping("/orders/{Id}")
	public Optional<Orders> getOrdersById(@PathVariable("Id") Long Id){
		
		log.info("Entering getOrdersById Controller");
		
		Optional<Orders> orders = orderservice.getById(Id);
		
		log.info("orders Id invoked: " + orders);
		
		if (!orders.isPresent())
			throw new OrderNotFoundException("Id" + Id);
		else {
			return orders;
		}

	
	}
	
	/*
	 * To display the Orders with items by ID from repo
	 */
	
	@GetMapping("/orders/{Id}/items")
	public List<Items> getOrdersAndItemsById(@PathVariable("Id") Long Id){
		log.info("Entering getOrdersAndItemsById Controller");
		Optional<Orders> orders = repo.findById(Id);
		
		log.info("orders Id invoked: " + orders);
		
		if (!orders.isPresent())
			throw new OrderNotFoundException("Id" + Id);
		else {
			return orders.get().getItems();
		}
	
	}	
	
	
	/*
	 * METHOD TO SAVE A SINGLE ITEM TO AN ORDER  
	 */
	
	
	@PostMapping(path = "/orders/create/{Id}", consumes = {"application/json"})
	@ResponseBody
	public ResponseEntity<Object> createItemsForOrders(@PathVariable ("Id") Long Id, @RequestBody Items items) {		
		
		log.info("Entering createItemsForOrders Controller");
		Optional<Orders> OrdersId = repo.findById(Id);	

		if(!OrdersId.isPresent()) {
			throw new OrderNotFoundException("Id" + Id);
			
		}else {
			
			Orders orders = OrdersId.get();		
				
			((Items) items).setOrders(orders);
			
			//itemsrepo.save(items);
			orderservice.saveItems(items);
		}
		
		return new ResponseEntity<Object>(items, HttpStatus.OK);
		
	}
	
	
	
	/*
	 * METHOD TO SAVE MANY ITEMS TO AN ORDER
	 */
	
	@PostMapping("/order")	
	private String saveOrder(@RequestBody Orders order) {
		log.info("Entering saveOrder Controller");
		for(Items items : order.getItems()) {
		
			boolean check = orderservice.CheckProduct((items.getId()));
			System.out.println(check);
			if (!check) {
				
				return "Product :"+items.getName()+" is not available to book. Order cannot be placed";				 
			}			
		}	

		orderservice.save(order);
		return "Order Posted Successfully";
	}
	
	
	/*
	 * To test Hystrix with inventory API
	 */
	
	@PostMapping("/TestHystrix")
	private String testHystrix(@RequestBody Orders order) {
		log.info("Entering testHystrix Controller");
		String check =null;
		for(Items items : order.getItems()) {
			check = orderservice.TestHystrix((items.getId()));
			//System.out.println(check);
			if (check.equalsIgnoreCase("true") || check.equalsIgnoreCase("false")) {
				
				return "Service is available.";
				 
			}
		}
		
		return check;
	}

	/*
	 * To delete a particular order with order ID
	 */
	
		@DeleteMapping("/orders/{id}")
		private ResponseEntity<Object> deleteOrdersById(@PathVariable("id") Long id) {
			log.info("Entering deleteOrdersById Controller");
			Optional<Orders> delete = orderservice.getById(id);
			orderservice.delete(id);
			return new ResponseEntity<Object>(delete, HttpStatus.OK);
		}
	
		/*
		 *  *********** Unit Testing ***********
		 */


//		@GetMapping("/testgetOrderById/{Id}")
//		private void getOrderByIdTest(@PathVariable("Id") Long Id) {
//			repo.findById(Id);			
//
//		}
		
		@PostMapping("/testOrderSave")
		private Orders savetest(@RequestBody Orders order) {
			orderservice.save(order);
			return order;
	
		}
	
}
