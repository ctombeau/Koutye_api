package com.chrisnor.koutye.exception;

public class FileNotFoundException extends RuntimeException{
    
	 public FileNotFoundException()
	 {
		 super("Fichier a telecharger non trouve");
	 }
}
