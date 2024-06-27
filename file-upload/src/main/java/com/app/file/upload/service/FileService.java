package com.app.file.upload.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.file.upload.entity.File;
import com.app.file.upload.entity.User;

public interface FileService {

	 public List<File> findAllFiles();

	 public File findFileByName(String filename);

	 public void saveFile(MultipartFile file, Long userId, String userDirectory);

	 public void deleteFile(Long id);
}
