package com.app.file.upload.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.file.upload.config.FileUploadConfig;
import com.app.file.upload.entity.File;
import com.app.file.upload.exception.FileStorageException;
import com.app.file.upload.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService{

	

	
    @Autowired
    private FileRepository repoObj;

    @Autowired
    private FileUploadConfig fileUploadConfig;
    
    
	@Override
	public List<File> findAllFiles() {
		return repoObj.findAll();
	}

	@Override
	public File findFileByName(String filename) {
		return repoObj.findByFilename(filename);
	}

//	
//	@Override
//	public void saveFile(MultipartFile file, Long userId) {
//		 String filename = file.getOriginalFilename();
//		    if (filename == null || filename.isEmpty()) {
//		        throw new FileStorageException("File name must not be empty");
//		    }
//		    if (findFileByName(filename) != null) {
//		        throw new FileStorageException("File with the same name already exists");
//		    }
//		    String[] allowedExtensions = {"txt", "xls", "xlsx","pdf"};
//		    String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
//		    boolean isAllowed = false;
//		    for (String ext : allowedExtensions) {
//		        if (ext.equals(fileExtension)) {
//		            isAllowed = true;
//		            break;
//		        }
//		    }
//		    if (!isAllowed) {
//		        throw new FileStorageException("File format not allowed");
//		    }
//
//		    try {
//		        Path uploadPath = Paths.get("uploads");
//		        // Create the directory if it does not exist
//		        if (!Files.exists(uploadPath)) {
//		            Files.createDirectories(uploadPath);
//		        }
//		        // Write the file to the directory
//		        Path filePath = uploadPath.resolve(filename);
//		        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
//
//		        // Save file details in the database
//		        File fileObj = new File();
//		        fileObj.setUserId(userId);
//		        fileObj.setFilename(filename);
//		        fileObj.setTimeOfUpload(LocalDateTime.now());
//		        fileObj.setFileType(file.getContentType());
//		        fileObj.setFileSize(file.getSize());
//		        repoObj.save(fileObj);
//		    } catch (IOException e) {
//		        throw new FileStorageException("File upload failed: " + e.getMessage());
//		    }
//
//		
//	}
//	
//	@Override
//	public void saveFile(MultipartFile file, Long userId, String userDirectory) {
//		
//		String directoryToUse = (userDirectory != null && !userDirectory.isEmpty()) ? userDirectory : fileUploadConfig.getUploadDirectory();
//
//	    String filename = file.getOriginalFilename();
//	    if (filename == null || filename.isEmpty()) {
//	        throw new FileStorageException("File name must not be empty");
//	    }
//	    if (findFileByName(filename) != null) {
//	        throw new FileStorageException("File with the same name already exists");
//	    }
//	    
//	    String[] allowedExtensions = {"txt", "xls", "xlsx", "pdf"};
//	    String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
//	    boolean isAllowed = false;
//	    for (String ext : allowedExtensions) {
//	        if (ext.equals(fileExtension)) {
//	            isAllowed = true;
//	            break;
//	        }
//	    }
//	    if (!isAllowed) {
//	        throw new FileStorageException("File format not allowed");
//	    }
//
//	    try {
//	        Path userPath = Paths.get(userDirectory);
//	        // Create the directory if it does not exist
//	        if (!Files.exists(userPath)) {
//	            Files.createDirectories(userPath);
//	        }
//	        // Write the file to the user-specified directory
//	        Path filePath = userPath.resolve(filename);
//	        Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
//
//	        // Save file details in the database
//	        File fileObj = new File();
//	        fileObj.setUserId(userId);
//	        fileObj.setFilename(filename);
//	        fileObj.setTimeOfUpload(LocalDateTime.now());
//	        fileObj.setFileType(file.getContentType());
//	        fileObj.setFileSize(file.getSize());
//	        repoObj.save(fileObj);
//	    } catch (IOException e) {
//	        throw new FileStorageException("File upload failed: " + e.getMessage());
//	    }
//	}
	
	 @Override
	    public void saveFile(MultipartFile file, Long userId, String userDirectory) {
	        String filename = file.getOriginalFilename();
	        if (filename == null || filename.isEmpty()) {
	            throw new FileStorageException("File name must not be empty");
	        }
	        if (findFileByName(filename) != null) {
	            throw new FileStorageException("File with the same name already exists");
	        }

	        String[] allowedExtensions = {"txt", "xls", "xlsx", "pdf"};
	        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
	        boolean isAllowed = false;
	        for (String ext : allowedExtensions) {
	            if (ext.equals(fileExtension)) {
	                isAllowed = true;
	                break;
	            }
	        }
	        if (!isAllowed) {
	            throw new FileStorageException("File format not allowed");
	        }

	        try {
	            // Use the provided userDirectory if not null or empty; otherwise, use the default from config
	            String directoryToUse = (userDirectory != null && !userDirectory.isEmpty()) ? userDirectory : fileUploadConfig.getUploadDirectory();

	            Path userPath = Paths.get(directoryToUse);
	            // Create the directory if it does not exist
	            if (!Files.exists(userPath)) {
	                Files.createDirectories(userPath);
	            }
	            // Write the file to the user-specified directory
	            Path filePath = userPath.resolve(filename);
	            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

	            // Save file details in the database
	            File fileObj = new File();
	            fileObj.setUserId(userId);
	            fileObj.setFilename(filename);
	            fileObj.setTimeOfUpload(LocalDateTime.now());
	            fileObj.setFileType(file.getContentType());
	            fileObj.setFileSize(file.getSize());
	            repoObj.save(fileObj);
	        } catch (IOException e) {
	            throw new FileStorageException("File upload failed: " + e.getMessage());
	        }
	    }


	@Override
	public void deleteFile(Long id) {
		File fileObj = repoObj.findById(id)
                .orElseThrow(() -> new FileStorageException("File not found"));
        java.io.File file = new java.io.File("uploads/" + fileObj.getFilename());
        if (file.delete()) {
        	repoObj.delete(fileObj);
        } else {
            throw new FileStorageException("Failed to delete the file");
        }
    }

	
}
