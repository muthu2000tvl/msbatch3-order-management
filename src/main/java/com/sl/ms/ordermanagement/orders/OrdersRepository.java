package com.sl.ms.ordermanagement.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>{

	public Orders findByName(String name);

	//Orders saveAll(List<Orders> orders);
	
	

}
