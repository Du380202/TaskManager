package com.web.webservice.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.webservice.api.ProjectAPI;
import com.web.webservice.api.TaskAPI;
import com.web.webservice.api.UserAPI;
import com.web.webservice.dto.ActivityDto;
import com.web.webservice.dto.ProjectDto;
import com.web.webservice.dto.TaskDetailDto;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.dto.UserDto;
import com.web.webservice.model.CustomUserDetails;
import com.web.webservice.model.ResponseObject;

import jakarta.websocket.server.PathParam;

@Controller
public class TaskController {
	@Autowired
	private TaskAPI taskAPI;
	@Autowired
	private ProjectAPI projectAPI;
	
	@Autowired
	private UserAPI userApi;
	@GetMapping("/listTask")
	public String index(Model model,@PathParam("keyword") String keyword) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		model.addAttribute("user", user);
		List<TaskDetailDto> tasks = taskAPI.getDataTaskDetail();
		
		if(keyword != null) {
			tasks = taskAPI.searchTask(keyword);
			model.addAttribute("key", keyword);
		}
		model.addAttribute("tasks", tasks);
		if (user.getRole().equals("user")) {
			return "user/listTask";
		}
		return "listTask";
	}
	
	@GetMapping("/allTask")
	public String indexUser(Model model) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		model.addAttribute("user", user);
		List<TaskDto> tasks = taskAPI.getTaskByUser(user.getUserId());
		model.addAttribute("taskss", tasks);
		return "user/listTask";
	}
	
	
	@GetMapping("/assignment")
	public String assignment(Model model) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		List<UserDto> users = userApi.getUserByRole();
		List<ProjectDto> projects = projectAPI.getAllProject();
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("projects", projects);
		model.addAttribute("taskRequest", new TaskDto());
		return "assignment";
	}
	
	@PostMapping("/create")
	public String createNew(@ModelAttribute("taskRequest") TaskDto task, 
			RedirectAttributes redirectAttributes, Model model) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		model.addAttribute("user", user);
		long millis = System.currentTimeMillis();
	    Date currentDate = new Date(millis);
	    task.setUpdatedAt(currentDate);
	    boolean check = taskAPI.createNew(task);
	    System.out.println(check);
	    if(check) {
	    	redirectAttributes.addFlashAttribute("Success", "Thêm công việc thành công");
	    }
	    else {
	    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
	    }
		return "redirect:/listTask";
	}
	
	@PostMapping("/update")
	public String updateTask(@ModelAttribute("task") TaskDto task,
			RedirectAttributes redirectAttributes) {
		long millis = System.currentTimeMillis();
	    Date currentDate = new Date(millis);
	    task.setUpdatedAt(currentDate);
	    System.out.println(task.getStatus());
	    boolean check = taskAPI.update(task.getTaskId(), task);
	    if(check) {
	    	redirectAttributes.addFlashAttribute("Success", "Successfull");
	    }
	    else {
	    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
	    }
		return "redirect:/listTask";
	}
	
	@GetMapping("/detail") 
	public String viewDetail(Model model, @RequestParam("taskId") Integer taskId) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		List<UserDto> users = userApi.getUserByRole();
		TaskDto task = taskAPI.getTaskById(taskId);
		List<ActivityDto> activities = taskAPI.getActivities(taskId); 
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("task", task);
		model.addAttribute("activities", activities);
		return "detail";
	}
	
	@GetMapping("/edit") 
	public String editDetail(Model model, @RequestParam("taskId") Integer taskId) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		List<UserDto> users = userApi.getUserByRole();
		TaskDto task = taskAPI.getTaskById(taskId);
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("task", task);
		return "editTask";
	}
	
	@GetMapping("/detailTask") 
	public String viewDetailTask(Model model, @RequestParam("taskId") Integer taskId) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		TaskDto task = taskAPI.getTaskById(taskId);
		List<ProjectDto> list = projectAPI.getAllProject();
		List<ActivityDto> activities = taskAPI.getActivities(taskId); 
		model.addAttribute("user", user);
		model.addAttribute("task", task);
		
		model.addAttribute("activities", activities);
		return "user/detail";
	}
	
	@PostMapping("/updateTaskUser")
	public String updateTaskUser(@ModelAttribute("task") TaskDto task, @RequestParam("descrip") String descrip,
			RedirectAttributes redirectAttributes) {
		long millis = System.currentTimeMillis();
	    Date currentDate = new Date(millis);
	    task.setUpdatedAt(currentDate);
	    System.out.println(descrip);
	    task.setDescription(descrip);
	    System.out.println(task.getDescription());
	    
	    boolean check = taskAPI.updateTask(task.getTaskId(), task);
	    if(check) {
	    	redirectAttributes.addFlashAttribute("Success", "Successfull");
	    }
	    else {
	    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
	    }
		return "redirect:/listTask";
	}
}
