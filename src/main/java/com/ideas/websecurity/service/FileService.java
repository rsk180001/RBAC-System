package com.ideas.websecurity.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ideas.websecurity.dto.FileMetadata;

@Service
public class FileService {
    public List<FileMetadata> getAllFiles()
    {	
		  List<FileMetadata> files = new ArrayList<>(); 
		  File file = new File("F:/E/JAVA/files/"); String[] fileList = file.list(); 
		  for(String name : fileList) 
		  { files.add(new FileMetadata(name,"F:/E/JAVA/files/")); }
		 
        return files;
    }

    public FileMetadata getFileByName(String name) 
    {
        return new FileMetadata("file1", "path1");
    }

    public boolean deleteFileByName(String name) 
    {
    	File file = new File("F:/E/JAVA/files/" +name); 
    	try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
        if(file.delete()) 
        { 
            System.out.println("File deleted successfully"); 
            return true;
        } 
        else
        { 
            System.out.println("Failed to delete the file"); 
            return false;
        } 
    }

    public FileMetadata storeFile(MultipartFile file)
    {
    	String name = file.getOriginalFilename();
    	File dest = new File("F:\\E\\Java\\files\\" +name);
    	try 
    	{
			file.transferTo(dest);
		} 
    	catch (IllegalStateException e) 
    	{
			e.printStackTrace();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
        return new FileMetadata(name, "F:\\E\\Java\\files\\");
    }

    public String getReadFileByName(String name) 
    {
    	File file = new File("F:\\E\\Java\\files\\"+name); 
		  try 
		  {
	    	  BufferedReader br = new BufferedReader(new FileReader(file)); 
	    	  StringBuilder sb = new StringBuilder();
	    	  String st; 
	    	  while ((st = br.readLine()) != null) 
	    	    sb.append(st); 
	    	  return sb.toString();
		  }
		  catch(Exception e)
		  {
			  System.out.println("File Not Found");
		  }
        return ""; 
    }
}
