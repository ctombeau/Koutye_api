package com.chrisnor.koutye.file;

import static java.nio.file.Paths.get;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
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

import com.chrisnor.koutye.exception.FileExcededSizeException;
import com.chrisnor.koutye.exception.FileNotFoundException;


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
		catch(Exception e)
		{
			throw new FileNotFoundException();
		}
	
	}
	
	
	public String UploadFiles(MultipartFile multipartFile, String directory)
			throws FileNotFoundException {
         Path filePath=null;
         System.out.println("Size: "+ multipartFile.getSize());
         System.out.println("Content: "+ multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {
        	if(!multipartFile.getOriginalFilename().equals(""))
        	{
              filePath= Path.of(directory+"\\"+multipartFile.getOriginalFilename()).toAbsolutePath().normalize();
              File file = new File(filePath.toString());
			  boolean testDirectory= file.mkdirs();
			  if (testDirectory)
                 Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
               return filePath.toString();
        	}
        	else
        		throw new FileNotFoundException();
        } catch (IOException ioe) {       
            //throw new IOException("Could not save file: " + multipartFile.getOriginalFilename(), ioe);
        	throw new FileNotFoundException();
        }
         
        
	}
	
	public List<String> UploadAllFiles(List<MultipartFile> mfs, String directory)
			throws FileNotFoundException {
         List<String> filePaths= new ArrayList<>();
         //mfs.forEach(x->System.out.println(x.getOriginalFilename()));
         
         mfs.forEach(mf->{
        	 if((mf.getContentType().contains("video") && mf.getSize()<= 10120000) || mf.getContentType().contains("image")) {
        		 
        		 try(InputStream inputStream = mf.getInputStream()){
           		  if(!mf.getOriginalFilename().equals("")) {
           			  
           			  Path filePath= Path.of(directory+"\\"+mf.getOriginalFilename()).toAbsolutePath().normalize();
           			  File file = new File(filePath.toString());
           			  boolean testDirectory= file.mkdirs();
           			  
           			  if(testDirectory) {
           			    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
           			 
           			    filePaths.add(filePath.toString());
           			  }
           		  }
           		  else
                 		throw new FileNotFoundException();
           	  }
           	  catch (IOException ioe) {       
                     //throw new IOException("Could not save file: " + multipartFile.getOriginalFilename(), ioe);
                 	throw new FileNotFoundException();
                 }
        	 }
        	 else 
        		 throw new FileExcededSizeException();
         });
         
         return filePaths;
	}
	
	public boolean deleteFile(String path)
	{
		File file = new File(path);
		return file.delete();
	}
	
	public static InputStream DownloadFiles(String path) throws IOException
	{ 
		if(!path.equals(""))
		{
			Path filePath = Paths.get(path);
			String fileName = filePath.getFileName().toString();
			File file = new File(path);
			
			Path destination = Paths.get("C:/Koutye_Folder/Destination/");
			
			if(!Files.exists(filePath))
			{
				throw new FileNotFoundException();
			}
			InputStream resource = new FileInputStream(path);
			Files.copy(resource, destination, StandardCopyOption.REPLACE_EXISTING);
		    return resource;
		}
		else
			throw new FileNotFoundException();
	}

}
