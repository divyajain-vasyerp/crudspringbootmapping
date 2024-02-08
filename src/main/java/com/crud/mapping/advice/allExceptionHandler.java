package com.crud.mapping.advice;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crud.mapping.dto.ResponseDTO;
import com.crud.mapping.exception.BookIsAlreadyAssignedException;
import com.crud.mapping.exception.NoDataFoundException;
import com.crud.mapping.exception.StudentNotFoundException;

@RestControllerAdvice
public class allExceptionHandler {
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler
	public ResponseDTO studentNotFoundExceptionHandling(StudentNotFoundException ex)
	{
		return new ResponseDTO(404,"error",ex.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ResponseDTO nullPointerException(NullPointerException ex)
	{
		return new ResponseDTO(500,"error",ex.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler
	public ResponseDTO noDataFoundException(NoDataFoundException ex)
	{
		return new ResponseDTO(510,"error",ex.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler
	public ResponseDTO bookIsAlreadyAssignedException(BookIsAlreadyAssignedException ex)
	{
		return new ResponseDTO(510,"error",ex.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ResponseDTO ioException(IOException ex)
	{
		return new ResponseDTO(500,"error",ex.getMessage());
	}

}
