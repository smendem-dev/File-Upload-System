package com.app.file.upload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.file.upload.exception.FileStorageException;
import com.app.file.upload.service.FileService;


@Controller
@RequestMapping("/api")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping("/home")
    public String homePage(Model model, @RequestParam("userId") Long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("files", fileService.findAllFiles());
        return "home";
    }

//    @PostMapping("/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId, Model model) {
//        try {
//            fileService.saveFile(file, userId);
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "home";
//        }
//        return "redirect:/api/home?userId=" + userId;
//    }

//    
//    @PostMapping("/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file,
//                             @RequestParam("userId") Long userId,
//                             @RequestParam("userDirectory") String userDirectory,
//                             Model model) {
//        try {
//            fileService.saveFile(file, userId, userDirectory);
//        } catch (Exception e) {
//            model.addAttribute("error", "Error occurred while uploading file: " + e.getMessage());
//            return "home";  // Return to home page with error message
//        }
//        return "redirect:/api/home?userId=" + userId;  // Redirect to home page after successful upload
//    }
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("userId") Long userId,
                             @RequestParam("userDirectory") String userDirectory,
                             Model model) {
        try {
            fileService.saveFile(file, userId, userDirectory);
        } catch (FileStorageException e) {
            model.addAttribute("error", "Error occurred while uploading file: " + e.getMessage());
            model.addAttribute("userId", userId);
            model.addAttribute("files", fileService.findAllFiles()); // Ensure files are still loaded for display
            return "home"; // Return to home page with error message
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected error occurred: " + e.getMessage());
            model.addAttribute("userId", userId);
            model.addAttribute("files", fileService.findAllFiles()); // Ensure files are still loaded for display
            return "home"; // Return to home page with generic error message
        }
        return "redirect:/api/home?userId=" + userId; // Redirect to home page after successful upload
    }

    @PostMapping("/delete/{id}")
    public String deleteFile(@PathVariable Long id, @RequestParam("userId") Long userId, Model model) {
        try {
            fileService.deleteFile(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }
        return "redirect:/api/home?userId=" + userId;
    }
}
