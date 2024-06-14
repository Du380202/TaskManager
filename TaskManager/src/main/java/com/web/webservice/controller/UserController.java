package com.web.webservice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.webservice.api.TaskAPI;
import com.web.webservice.api.UserAPI;
import com.web.webservice.dto.CreateUser;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.dto.UserDto;
import com.web.webservice.integration.User_Task;
import com.web.webservice.model.CustomUserDetails;

@Controller
public class UserController {
	@Autowired
	private UserAPI userApi;
	
	@Autowired
	private TaskAPI taskApi;
	 User_Task userTask = new User_Task();
	
	@GetMapping("/employee")
	public String employee(Model model) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		model.addAttribute("user", user);
		List<UserDto> users = userApi.getUserByRole();
		model.addAttribute("users", users);
		return "employee";
	}
	
	@GetMapping("/createUser")
	public String addUser(Model model) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		
		model.addAttribute("newUser", new CreateUser());
		model.addAttribute("user", user);
		
		return "addUser";
	}
	
	@PostMapping("/createUser")
	public String createNew(@ModelAttribute("newUser") UserDto newUser, 
			RedirectAttributes redirectAttributes, Model model) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		model.addAttribute("user", user);
		newUser.setCreatedAt(new Date());
		System.out.println(newUser.getImg());
		boolean check = userApi.createNew(newUser);
		if(check) {
	    	redirectAttributes.addFlashAttribute("Success", "Successfull");
	    }
	    else {
	    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
	    }
		return "redirect:/employee";
	}
	
	@GetMapping("/detailUser")
	public String detailUser(Model model, @RequestParam("userId") Integer userId) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetails.getUser();
		UserDto userdetail = userApi.getUserById(userId);
		List<TaskDto> res = userTask.getAllByUserId(taskApi, userId);
		model.addAttribute("user", user);
		model.addAttribute("userDetail", userdetail);
		model.addAttribute("taskUser", res);
		return "detailUser";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute("updateUser") UserDto updateUser, 
			RedirectAttributes redirectAttributes, Model model) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetails.getUser();
		model.addAttribute("user", user);
		boolean check = userApi.updateUser(updateUser.getUserId(), updateUser);
		if(check) {
	    	redirectAttributes.addFlashAttribute("Success", "Successfull");
	    }
	    else {
	    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
	    }
		return "redirect:/employee";
	}
	
	@GetMapping("/account") 
	public String account(Model model) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetails.getUser();
		model.addAttribute("user", user);
		return "account";
	}
	
	@PostMapping("/chance") 
	public String chancePassword(Model model, @RequestParam("currentPassword") String password,
			@RequestParam("confirmNewPassword") String confirmNewPassword,
			RedirectAttributes redirectAttributes) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetails.getUser();
		System.out.println("Pass: " + confirmNewPassword);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		if (passwordEncoder.matches(password, user.getPassword())) {
			boolean check = userApi.chancePassword(user.getUserId(), confirmNewPassword);
		    if(check) {
		    	redirectAttributes.addFlashAttribute("Success", "Successfull");
		    }
		    else {
		    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
		    }
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
		    redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
		} 
		
		return "redirect:/account";
	}
}
