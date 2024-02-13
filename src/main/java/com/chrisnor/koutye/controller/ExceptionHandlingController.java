package com.chrisnor.koutye.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.exception.ApiError;
import com.chrisnor.koutye.exception.InvalidInputException;

@RestController
@ControllerAdvice
public class ExceptionHandlingController {
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ApiError> handleInvalidInputException(){
		
		ApiError apiError = new ApiError(400,"informations saisies incorrectes", LocalDateTime.now());
		return new ResponseEntity<ApiError>(apiError,HttpStatus.BAD_REQUEST);
	}
	

}
