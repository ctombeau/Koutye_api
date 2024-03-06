package com.chrisnor.koutye.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseGenerator {
	
	public ResponseEntity<Response> ErrorResponse(HttpStatus status, String message)
	{
		Response res = new Response();
		res.setSuccess(false);
		res.setMessage(message);
		return ResponseEntity.status(status).body(res);
	}
	
	public ResponseEntity<Response> SuccessResponse(HttpStatus status, Object object)
	{
		Response res = new Response();
		res.setObject(object);
		res.setSuccess(true);
		return ResponseEntity.status(status).body(res);
	}

}
