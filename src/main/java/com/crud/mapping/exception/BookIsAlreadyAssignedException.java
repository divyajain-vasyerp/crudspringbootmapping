package com.crud.mapping.exception;

import lombok.Data;

@Data
public class BookIsAlreadyAssignedException extends RuntimeException{

	
	public BookIsAlreadyAssignedException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookIsAlreadyAssignedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}

