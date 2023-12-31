package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Security.PasswordHash;
import com.example.demo.Session.HttpSessionBean;
import com.example.demo.dbService.User;
import com.example.demo.dbService.UserService;

import org.springframework.ui.Model; 

@Controller
public class SecurityController{
    
    @Autowired 
    private HttpSessionBean httpSessionBean;

    @Autowired
    private UserService userService;    

    @GetMapping("/auth/signup")
    public String SingUp(Model model) {

        return "signup";
    }
    @GetMapping("/auth/login")
    public String LogIn(Model model) {

        return "login";
    }
    
    @PostMapping("/auth/signup")
    public String SingUp(@RequestParam String username, @RequestParam String password,@RequestParam String email, Model model) {
        if (userService.findByUsername(username) != null) { 
            return "signup";
        }
        String hashedPassword=PasswordHash.hashPassword(password);
        

        User user = new User(username, hashedPassword, email, "ROLE_USER");
        userService.createUser(user);

        return "redirect:/films/auth/login";
    }

    @PostMapping("/auth/login")
    public String LogIn(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            
            return "login";
        }
        
        httpSessionBean.setName(username);
        
        if (username.equals(user.getUsername()) && PasswordHash.isPasswordMatch(password, user.getPassword())) {
            return "redirect:/films/allFilms";
        }
        
    
        return "login";
    }
}
