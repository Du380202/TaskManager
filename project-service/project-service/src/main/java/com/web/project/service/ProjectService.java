package com.web.project.service;

import java.util.List;

import com.web.project.dto.ActivityDto;
import com.web.project.dto.ProjectDto;
import com.web.project.entity.Project;

public interface ProjectService {
	public String countProject();
	public Project findById(Integer id);
	public List<Project> findAll();
	public boolean createNew(ProjectDto projectDto);
	public boolean update(Integer id,Project projectDto);
	public boolean updateProject(Integer id,Project projectDto);
	public List<ActivityDto> getActiviTyByTask(Integer taskId);
	public List<Project> searchProject(String key);
	public Long getInProcessProjectsCount();
	public Long getCompletedProjectsCount();
	public Long getCancelProjectsCount();
}
