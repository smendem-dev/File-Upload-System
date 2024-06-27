package com.app.file.upload.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.file.upload.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	File findByFilename(String filename);

}
