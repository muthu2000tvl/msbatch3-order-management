package com.sl.ms.ordermanagement.orders;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sl.ms.ordermanagement.items.Items;

@Entity(name = "SL_ORDERS")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TOTAL_AMOUNT")
	private int total_amount;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.PERSIST,mappedBy="orders",orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	private List<Items> items;

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

	public Orders() {
		
	}

	public Orders(Long id, String name, int total_amount) {
		super();
		this.id = id;
		this.name = name;
		this.total_amount = total_amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal_amount() {
		return total_amount;
	}
	
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", name=" + name + ", total_amount=" + total_amount + "]";
	}





	

}
