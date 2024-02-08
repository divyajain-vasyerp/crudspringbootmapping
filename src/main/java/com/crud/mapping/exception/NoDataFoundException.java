package com.crud.mapping.exception;

import lombok.Data;

@Data
public class NoDataFoundException extends RuntimeException{

	public NoDataFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NoDataFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
