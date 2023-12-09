package com.chrisnor.koutye.response;

import lombok.Data;

@Data
public class Response {
   private boolean success;
   private Object object;
   private String message;
   
   
}
