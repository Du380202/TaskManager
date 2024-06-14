package com.web.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.webservice.service.CustomUserDetailsService;

@Controller
public class LoginController {

	@Autowired
    private CustomUserDetailsService customUserDetailsService;
	
    @GetMapping("/login")
    public String login() {
        return "login"; // Tên của template
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password) {
    	customUserDetailsService.loadUserByUsername(username);
        return "redirect:/"; 
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
    
    
}
