package com.crud.mapping.dto;

import lombok.Data;

@Data
public class StudentDTO {

	private Long id;
	private String rollNo;
	private String name;
	private String email;
	private long number;
    private LibraryDTO library;
}
