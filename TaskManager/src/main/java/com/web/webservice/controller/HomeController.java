package com.web.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.webservice.api.ProjectAPI;
import com.web.webservice.api.TaskAPI;
import com.web.webservice.api.UserAPI;
import com.web.webservice.dto.ProjectDto;
import com.web.webservice.dto.TaskDetailDto;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.dto.UserDto;
import com.web.webservice.model.CustomUserDetails;


@Controller
public class HomeController {
	@Autowired
	private ProjectAPI projectService;
	@Autowired
	private TaskAPI taskAPI;
	
	@Autowired
	private UserAPI userAPI;
	
	@GetMapping("")
	public String index(Model model) {
		String s = projectService.callCountProject();
		CustomUserDetails userDetail =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDto user = userDetail.getUser();
		List<ProjectDto> projects = projectService.getAllProject();
		List<UserDto> users = userAPI.getUserByRole();
		int tasks = taskAPI.getAll().size();
		model.addAttribute("user", user);
		model.addAttribute("projects", projects);
		model.addAttribute("countPro", s);
		model.addAttribute("countUser", users.size());
		model.addAttribute("countTask", tasks);
		Integer completedTasks = taskAPI.getCountCompleted();
		Integer incompleteTasks = taskAPI.getCountProcess();
		Integer inCancelTasks = taskAPI.getCountCancell();
		Integer completedproject = projectService.getCountCompleted();
		Integer incompleted = projectService.getCountProcess();
		Integer inCancelProject = projectService.getCountCancell();
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("incompleteTasks", incompleteTasks);
        model.addAttribute("inCancelTasks", inCancelTasks);
        
        
        model.addAttribute("completedProjects", completedproject);
        model.addAttribute("incompleteProjects", incompleted);
        model.addAttribute("inCancelProjects", inCancelProject);
		if (user.getRole().equals("user")) {
			List<TaskDto> taskss = taskAPI.getTaskByUser(user.getUserId());
			model.addAttribute("countTask", taskss.size());
			return "user/index";
		}
		else {
			return "index";
		}
		
	}
	
	
}
