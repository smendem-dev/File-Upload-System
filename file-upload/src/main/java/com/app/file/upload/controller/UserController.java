package com.app.file.upload.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.file.upload.entity.User;
import com.app.file.upload.service.UserService;


@Controller
@RequestMapping("/api")
public class UserController {
	
    @Autowired
    private UserService serviceObj;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
//    /file-upload/src/main/resources/templates/home.html

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (serviceObj.validateUser(username, password)) {
            User user = serviceObj.findByUsername(username);
            Long userId = user.getId();
            return "redirect:/api/home?userId=" + userId;
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
    

}
