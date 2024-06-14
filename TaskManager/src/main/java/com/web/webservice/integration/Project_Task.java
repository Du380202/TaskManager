package com.web.webservice.integration;

import org.springframework.beans.factory.annotation.Autowired;

import com.web.webservice.api.ProjectAPI;
import com.web.webservice.dto.ProjectDto;

public class Project_Task {
	@Autowired
	ProjectAPI project;
	
	public String getProjectName(Integer projectId) {
		ProjectDto pro = project.getById(projectId);
		
		return pro.getProjectName();
	}
}
