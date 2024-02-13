package com.chrisnor.koutye.exception;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException()
	{
		super("Utilisateur non trouve");
	}

}
