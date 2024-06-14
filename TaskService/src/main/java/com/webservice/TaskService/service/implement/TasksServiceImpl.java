package com.webservice.TaskService.service.implement;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.TaskService.*;
import com.webservice.TaskService.dto.ActivityDto;
import com.webservice.TaskService.dto.TaskDetailDto;
import com.webservice.TaskService.entity.ActivityLog;
import com.webservice.TaskService.entity.Task;
import com.webservice.TaskService.repository.ActivityRepository;
import com.webservice.TaskService.repository.TasksRepository;
import com.webservice.TaskService.service.TasksService;
import com.webservice.TaskService.serviceclient.UserClient;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.transaction.Transactional;


@Service
public class TasksServiceImpl implements TasksService {
	@Autowired
	TasksRepository taskRepository;
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	private UserClient user;
	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task findById(int id) {
		return taskRepository.findById(id);
	}

	@Override
	public boolean createNew(Task cv) {
		try {
			ActivityLog acLog = new ActivityLog();
			acLog.setTask(cv);
			acLog.setTimestamp(cv.getUpdatedAt());
			acLog.setAction(cv.getDescription());
			acLog.setUserId(cv.getAssigneeId());
			acLog.setStatus(cv.getStatus());
			Task task = taskRepository.save(cv);
			activityRepository.save(acLog);
			return true;
		} catch(Exception e) {
			throw new RuntimeException("Error while creating new project: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean updateTask(Integer taskId, Task updateTask) {
	    Optional<Task> existingTaskOpt = taskRepository.findById(taskId);
	    if (!existingTaskOpt.isPresent()) {
	        throw new EntityNotFoundException("Không tìm thấy công việc với ID: " + taskId);
	    }
	    try {
	    	Task existingTask = existingTaskOpt.get();
		    existingTask.setDescription(updateTask.getDescription());
		    existingTask.setStatus(updateTask.getStatus());
		    existingTask.setAssigneeId(updateTask.getAssigneeId());
		    existingTask.setUpdatedAt(updateTask.getUpdatedAt());
		    existingTask.setDueDate(updateTask.getDueDate());
		    existingTask.setTaskName(updateTask.getTaskName());

		    Task savedTask = taskRepository.save(existingTask);

		    ActivityLog acLog = new ActivityLog();
		    acLog.setTask(savedTask);
		    acLog.setStatus(savedTask.getStatus());
		    acLog.setTimestamp(savedTask.getUpdatedAt());
		    acLog.setAction("Updated Task: " + savedTask.getDescription());
		    acLog.setUserId(savedTask.getAssigneeId());
		    activityRepository.save(acLog);

		    return true;
	    } catch(Exception e) {
	    	throw new RuntimeException("Error while creating new project: " + e.getMessage(), e);
	    }
	    
	}
	
	@Override
	public List<TaskDetailDto> getTaskDetail() {
		List<Task> tasks = taskRepository.findAll();
		List<TaskDetailDto> taskDtos = new ArrayList<TaskDetailDto>();
		for (Task task : tasks) {
			String fullName = user.getNameUser(task.getAssigneeId());
			taskDtos.add(new TaskDetailDto(task.getTaskId(), task.getCreatedAt(),task.getStatus(), task.getDueDate(), task.getTaskName(), fullName));
		}
		return taskDtos;
	}
	
	@Override
	public boolean updateTaskUser(Integer taskId, Task updateTask) {
	    Optional<Task> existingTaskOpt = taskRepository.findById(taskId);
	    if (!existingTaskOpt.isPresent()) {
	        throw new EntityNotFoundException("Không tìm thấy công việc với ID: " + taskId);
	    }
	    try {
	    	Task existingTask = existingTaskOpt.get();
		    existingTask.setStatus(updateTask.getStatus());
		    existingTask.setUpdatedAt(updateTask.getUpdatedAt());
		    Task savedTask = taskRepository.save(existingTask);

		    ActivityLog acLog = new ActivityLog();
		    acLog.setTask(savedTask);
		    acLog.setStatus(savedTask.getStatus());
		    acLog.setTimestamp(savedTask.getUpdatedAt());
		    acLog.setAction("Updated Task: " + updateTask.getDescription());
		    acLog.setUserId(savedTask.getAssigneeId());
		    activityRepository.save(acLog);

		    return true;
	    } catch(Exception e) {
	    	throw new RuntimeException("Error while creating new project: " + e.getMessage(), e);
	    }
	    
	}

	
	@Override
	public List<ActivityDto> getActiviTyByTask(Integer taskId) {
		List<Object[]> objects = activityRepository.findByTaskId(taskId);
		List<ActivityDto> activities = new ArrayList<ActivityDto>();
		for (Object[] tmp : objects) {
		    Integer id = (tmp[0] != null) ? ((Number) tmp[0]).intValue() : null;
		    String action = (tmp[1] != null) ? ((String) tmp[1]) : null;
		    Date timestamp = (tmp[2] != null) ? (Date) tmp[2] : null;
		    Integer userId = (tmp[3] != null) ? (Integer) tmp[3] : null;
		    Integer task = (tmp[4] != null) ? (Integer) tmp[4] : null;
		    String status = (tmp[5] != null) ? (String) tmp[5] : null;
		    activities.add(new ActivityDto(id, action, timestamp, userId, task, status));
		    
		}
		return activities;
	}

	@Override
	public List<Task> getTaskByUser(Integer userId) {
		
		return taskRepository.getByUser(userId);
	}

	@Override
	public List<TaskDetailDto> getTaskByProjectId(Integer projectId) {
		List<Task> tasks = taskRepository.getTaskByProject(projectId);
		List<TaskDetailDto> taskDtos = new ArrayList<TaskDetailDto>();
		for (Task task : tasks) {
			String fullName = user.getNameUser(task.getAssigneeId());
			taskDtos.add(new TaskDetailDto(task.getTaskId(), task.getCreatedAt(),task.getStatus(), task.getDueDate(), task.getTaskName(), fullName));
		}
		return taskDtos;
	}

	@Override
	public Long getInProcessProjectsCount() {
		// TODO Auto-generated method stub
		return taskRepository.countInProcessProjects();
	}

	@Override
	public Long getCompletedProjectsCount() {
		// TODO Auto-generated method stub
		return taskRepository.countCompletedProjects();
	}

	@Override
	public Long getCancelProjectsCount() {
		// TODO Auto-generated method stub
		return taskRepository.countCancelProjects();
	}

	@Override
	public List<TaskDetailDto> searchTask(String key) {
		List<Task> tasks = taskRepository.searchTask(key);
		List<TaskDetailDto> taskDtos = new ArrayList<TaskDetailDto>();
		for (Task task : tasks) {
			String fullName = user.getNameUser(task.getAssigneeId());
			taskDtos.add(new TaskDetailDto(task.getTaskId(), task.getCreatedAt(),task.getStatus(), task.getDueDate(), task.getTaskName(), fullName));
		}
		return taskDtos;
	}
}
