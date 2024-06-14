package com.web.webservice.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.webservice.api.ProjectAPI;
import com.web.webservice.api.TaskAPI;
import com.web.webservice.dto.ActivityDto;
import com.web.webservice.dto.ProjectDto;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.dto.UserDto;
import com.web.webservice.model.CustomUserDetails;

import jakarta.websocket.server.PathParam;

@Controller
public class ProjectController {
	@Autowired
	private ProjectAPI projectAPI;
	
	@Autowired
	private TaskAPI taskAPI;
	
	@GetMapping("listProject")
	public String index(Model model, @PathParam("keyword") String keyword) {
		List<ProjectDto> projects = projectAPI.getAllProject();
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		if(keyword != null) {
			projects = projectAPI.searchProject(keyword);
			model.addAttribute("key", keyword);
		}
		model.addAttribute("user", user);
		model.addAttribute("projects", projects);
		return "project";
	}
	
	
	@GetMapping("/projectDetail")
	public String getProjectById(Model model, @RequestParam("projectId") Integer projectId) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		ProjectDto project = projectAPI.getById(projectId);
		List<TaskDto> tasks = taskAPI.getTaskByProject(projectId);
		List<ActivityDto> activities = projectAPI.getActivities(projectId); 
		model.addAttribute("user", user);
		model.addAttribute("project", project);
		model.addAttribute("tasks", tasks);
		model.addAttribute("success", "Lỗi");
		model.addAttribute("activities", activities);
		return "detailProject";
	}
	
	@GetMapping("/editProject")
	public String edit(Model model, @RequestParam("projectId") Integer projectId) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		ProjectDto project = projectAPI.getById(projectId);
		model.addAttribute("user", user);
		model.addAttribute("project", project);
		return "edit";
	}
	
	@PostMapping("/updateProject")
	public String updateTask(@ModelAttribute("project") ProjectDto project,
			RedirectAttributes redirectAttributes) {
		long millis = System.currentTimeMillis();
	    Date currentDate = new Date(millis);
	    project.setUpdatedAt(currentDate);
	    project.setStartDate(project.getCreatedAt());
	    System.out.println(project.getStatus());
	    boolean check = projectAPI.update(project.getProjectId(), project);
	    if(check) {
	    	redirectAttributes.addFlashAttribute("Success", "Successfull");
	    }
	    else {
	    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
	    }
		return "redirect:/listProject";
	}
	
	@GetMapping("/addProject")
	public String assignment(Model model) {
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		model.addAttribute("newProject", new ProjectDto());
		model.addAttribute("user", user);
		return "addProject";
	}
	
	@PostMapping("/addProject")
    public String create(@ModelAttribute("newProject") ProjectDto newProject, RedirectAttributes redirectAttributes, Model model) {
        CustomUserDetails userDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userDetail.getUser();
        model.addAttribute("user", user);
        newProject.setManager_id(user.getUserId());
        newProject.setStatus("In process");
        long millis = System.currentTimeMillis();
	    Date currentDate = new Date(millis);
	    newProject.setUpdatedAt(currentDate);
        boolean check = projectAPI.createNew(newProject);
        System.out.println(check);
        if (check) {
            redirectAttributes.addFlashAttribute("Success", "Thêm dự án thành công");
        } else {
            redirectAttributes.addFlashAttribute("Error", "Thêm dự án thất bại");
        }
        return "redirect:/listProject";
    }
	
	@PostMapping("/updateProjectUser")
	public String updateTaskUser(@ModelAttribute("project") ProjectDto project, @RequestParam("description") String descrip,
			RedirectAttributes redirectAttributes) {
		long millis = System.currentTimeMillis();
	    Date currentDate = new Date(millis);
	    System.out.println(descrip);
	    System.out.println(project.getTienDo());
	    project.setDescription(descrip);
	    project.setUpdatedAt(currentDate);
	    boolean check = projectAPI.updateProgress(project.getProjectId(), project);
	    if(check) {
	    	redirectAttributes.addFlashAttribute("Success", "Successfull");
	    }
	    else {
	    	redirectAttributes.addFlashAttribute("Error", "Invalid response from API");
	    }
		return "redirect:/listProject";
	}


}
