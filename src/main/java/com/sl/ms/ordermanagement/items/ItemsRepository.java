package com.sl.ms.ordermanagement.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;





public interface ItemsRepository extends JpaRepository<Items, Long>{	
	

	public Optional<Items> findById(Long Id);	
	
	

}
