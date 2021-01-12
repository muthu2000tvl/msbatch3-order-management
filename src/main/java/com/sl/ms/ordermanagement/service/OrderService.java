package com.sl.ms.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sl.ms.ordermanagement.items.Items;
import com.sl.ms.ordermanagement.items.ItemsRepository;
import com.sl.ms.ordermanagement.orders.Orders;
import com.sl.ms.ordermanagement.orders.OrdersRepository;

@Service
@Transactional
public class OrderService {
	
	private static final Logger log = LoggerFactory.getLogger(OrderService.class.getName());
	
	@Autowired
	OrdersRepository ordersrepo;
	
	
	@Autowired
	ItemsRepository itemsrepo;
	
	@Autowired
    private RestTemplate restTemplate;
	
	
	public Orders save(Orders order) {
		return ordersrepo.save(order);
		
	}
	
	public Items saveItems(Items items) {
		return itemsrepo.save(items);
		
	}
	
	/*
	 * Using rest template to call inventory API
	 */
		
		//private static RestTemplate restTemplate = new RestTemplate();
		
		
		public boolean CheckProduct(Long Id) {
			
			System.out.println(Id);
			log.info("Before calling the inventory API");
			
			String url = "http://localhost:7777/dev/checkProducts/";
			
			log.info("After calling the inventory API");
			
			String result = restTemplate.getForObject(url+Id,  String.class);
			
			System.out.println("-----------------"+ result);
			return Boolean.parseBoolean(result);
		}
		
	/*
	 * Inventory API call handler for Hystrix
	 */
	
		
	@HystrixCommand(fallbackMethod = "TestHystrixFallback")
	public String TestHystrix(Long Id) {
		
		//System.out.println(Id);
		String url = "http://localhost:7777/dev/checkProducts/";
		String result = restTemplate.getForObject(url+Id,  String.class);
		
		//System.out.println("-----------------"+ result);
		return result;		
		
	}
	
	public String TestHystrixFallback(Long Id) {
		
		return "Looks like service unavailable. Please try later";
		
	}
	
	public Optional<Orders> getById(Long id) {
		return ordersrepo.findById(id);
	}
	
	public void delete(Long id) {
		ordersrepo.deleteById(id);
	}

	public List<Orders> getOrders() {
		
		return ordersrepo.findAll();
	}
	
	public Optional<Orders> getByIdTest(Long id) {
		return ordersrepo.findById(id);
	}

}
