package com.web.webservice.api;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.web.webservice.dto.ActivityDto;
import com.web.webservice.dto.ProjectDto;
import com.web.webservice.dto.TaskDetailDto;
import com.web.webservice.dto.TaskDto;
import com.web.webservice.model.ResponseObject;

public interface TaskAPI{
	List<TaskDto> getAll();
	List<TaskDetailDto> getDataTaskDetail();
	boolean createNew(TaskDto newTask);
	
	boolean update(Integer taskId, TaskDto updateTask);
	
	boolean updateTask(Integer taskId, TaskDto updateTask);
	
	TaskDto getTaskById(Integer taskId);
	
	List<ActivityDto> getActivities(Integer taskId);
	
	List<TaskDto> getTaskByUser(Integer userId);
	
	List<TaskDto> getTaskByProject(Integer projectId);
	List<TaskDetailDto> searchTask(String key);
	Integer getCountProcess();
	Integer getCountCompleted();
	Integer getCountCancell();
}
