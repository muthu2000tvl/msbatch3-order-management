package com.sl.ms.ordermanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public OrderNotFoundException(String message) {
		super("Sorry No such " + message  + " itemID available in inventory. ");
		
	}
}
