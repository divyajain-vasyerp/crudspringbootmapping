package com.crud.mapping.exception;

import lombok.Data;

@Data
public class StudentNotFoundException extends RuntimeException{

	public StudentNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
