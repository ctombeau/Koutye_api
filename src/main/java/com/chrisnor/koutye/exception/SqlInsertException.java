package com.chrisnor.koutye.exception;


public class SqlInsertException extends RuntimeException {
	
	public SqlInsertException()
	{
		super("Erreur au moment de l'insertion...");
	}

}
