package com.web.webservice.api;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.web.webservice.dto.ActivityDto;
import com.web.webservice.dto.ProjectDto;
import com.web.webservice.dto.TaskDto;
@Service
public interface ProjectAPI {
	public String callCountProject();
	public ProjectDto getById(Integer id);
	public List<ProjectDto> getAllProject();
	public boolean createNew(ProjectDto newTask);
	public boolean update(Integer id, ProjectDto project);
	public boolean updateProgress(Integer id, ProjectDto project);
	List<ActivityDto> getActivities(Integer projectId);
	Integer getCountProcess();
	Integer getCountCompleted();
	Integer getCountCancell();
	List<ProjectDto> searchProject(String key);
}
