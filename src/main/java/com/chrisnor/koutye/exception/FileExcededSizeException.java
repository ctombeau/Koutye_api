package com.chrisnor.koutye.exception;

public class FileExcededSizeException extends RuntimeException{
	
	public FileExcededSizeException() {
		super("Le fichier depasse la taille maximum");
	}

}
