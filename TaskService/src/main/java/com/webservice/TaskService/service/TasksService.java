package com.webservice.TaskService.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webservice.TaskService.dto.ActivityDto;
import com.webservice.TaskService.dto.TaskDetailDto;
import com.webservice.TaskService.entity.ActivityLog;
import com.webservice.TaskService.entity.Task;
import com.webservice.TaskService.model.ResponseObject;


public interface TasksService {
	public List<Task> findAll();
	public Task findById(int id);
	public boolean createNew(Task cv);
	public boolean updateTask(Integer id, Task updateTask);
	public List<TaskDetailDto> getTaskDetail();
	public List<ActivityDto> getActiviTyByTask(Integer taskId);
	public List<Task> getTaskByUser(Integer userId);
	public boolean updateTaskUser(Integer taskId, Task updateTask);
	public List<TaskDetailDto> getTaskByProjectId(Integer projectId);
	
	public Long getInProcessProjectsCount();
	public Long getCompletedProjectsCount();
	public Long getCancelProjectsCount();
	
	public List<TaskDetailDto> searchTask(String task);
	
}
