package com.sl.ms.ordermanagement.items;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sl.ms.ordermanagement.orders.Orders;

@Entity(name = "SL_ITEMS")
public class Items {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
		
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "QUANTITY")
	private int quantity;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "AMOUNT")
	private double amount;
	
	

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	//@JsonIgnore
	@JsonBackReference
	@JoinColumn(name = "orders_id")
	private Orders orders;

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	protected Items() {
		
	}

	public Items(Long id,  String name, int quantity, double price, double amount) {
		super();
		this.id = id;		
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.amount = amount;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Items [id=" + id + ",  name=" + name + ", quantity=" + quantity + ", price="
				+ price + ", amount=" + amount + "]";
	}
	
	

}
