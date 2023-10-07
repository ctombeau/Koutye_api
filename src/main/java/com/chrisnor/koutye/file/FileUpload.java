package com.chrisnor.koutye.file;

import static java.nio.file.Paths.get;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public class FileUpload {
	
	public static void saveFile(String uploadDir, String FileName, MultipartFile multipartFile) throws IOException
	{
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath))
		{
			Files.createDirectories(uploadPath);	
		}
		try(InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(FileName);
			Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch(IOException e)
		{
			throw new IOException("Ne peut pas enregistrer le fichier "+ FileName,e);
		}
	
	}
	
	
	public String UploadFiles(MultipartFile multipartFile, String directory)
			throws IOException {
         Path filePath;
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
        	 System.out.println("Le chemin test: "+directory);
        	 filePath = Paths.get(directory,multipartFile.getOriginalFilename()).toAbsolutePath().normalize();
             Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {       
            throw new IOException("Could not save file: " + multipartFile.getOriginalFilename(), ioe);
        }
         
        return filePath.toString();
	}
	
	public static InputStream DownloadFiles(String path) throws IOException
	{
		Path filePath = Paths.get(path);
		String fileName = filePath.getFileName().toString();
		File file = new File(path);
		
		Path destination = Paths.get("C:/Koutye_Folder/Destination/");
		
		if(!Files.exists(filePath))
		{
			throw new FileNotFoundException("Le fichier "+fileName + " non trouve sur le serveur");
		}
		InputStream resource = new FileInputStream(path);
		Files.copy(resource, destination, StandardCopyOption.REPLACE_EXISTING);
	    return resource;
	}

}
