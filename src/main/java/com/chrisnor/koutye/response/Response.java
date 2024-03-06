package com.chrisnor.koutye.response;

import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class Response {
   private boolean success;
   //private HttpStatusCode httpCode;
   private Object object;
   private String message;
   
   
}
