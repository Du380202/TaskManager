package com.web.project.service.implement;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.project.dto.ActivityDto;
import com.web.project.dto.ProjectDto;
import com.web.project.entity.ActivityLog;
import com.web.project.entity.Project;
import com.web.project.repository.ActivityRepository;
import com.web.project.repository.ProjectRepository;
import com.web.project.service.ProjectService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ActivityRepository activityRepository;
	@Override
	public String countProject() {
		// TODO Auto-generated method stub
		return projectRepository.countProject() ;
	}
	
	@Override
	public Project findById(Integer id) {
		
		return projectRepository.findById(id).get();
	}
	
	@Override
	public List<Project> findAll() {
		
		return projectRepository.findAll();
	}
	@Override
	@Transactional
	public boolean createNew(ProjectDto projectDto) {
		try {
			Project p = new Project(projectDto.getProjectId(), projectDto.getProjectName(), projectDto.getDescription(),
					projectDto.getStartDate(), projectDto.getEndDate(), projectDto.getStatus(), projectDto.getCreatedAt(), 
					projectDto.getUpdatedAt(), projectDto.getManager_id(), 0);
			
			Project result = projectRepository.save(p);
			
			ActivityLog activity = new ActivityLog();
			activity.setAction(p.getProjectName());
			activity.setStatus(p.getStatus());
			activity.setProject(p);
			activity.setTimestamp(p.getUpdatedAt());
			activityRepository.save(activity);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Error while creating new project: " + e.getMessage(), e);
		}
		
	}
	
	@Override
	public boolean update(Integer id, Project projectDto) {
		try {
			Project p = projectRepository.findById(id).get();
			p.setCreatedAt(projectDto.getCreatedAt());
			p.setDescription(projectDto.getDescription());
			p.setEndDate(projectDto.getEndDate());
			p.setManager_id(projectDto.getManager_id());
			p.setProjectName(projectDto.getProjectName());
			p.setStartDate(projectDto.getCreatedAt());
			p.setStatus(projectDto.getStatus());
			p.setTienDo(projectDto.getTienDo());
			p.setUpdatedAt(projectDto.getUpdatedAt());
			
			ActivityLog activity = new ActivityLog();
			activity.setAction(projectDto.getProjectName());
			activity.setStatus(projectDto.getStatus());
			activity.setProject(p);
			activity.setTimestamp(p.getUpdatedAt());
			
			projectRepository.save(p);
			activityRepository.save(activity);
			return true;
		} catch(Exception e) {
			throw new RuntimeException("Error while creating new project: " + e.getMessage(), e);
			
		}
	}

	@Override
	public boolean updateProject(Integer id, Project projectDto) {
		try {
			Project p = projectRepository.findById(id).get();
			p.setStatus(projectDto.getStatus());
			p.setTienDo(projectDto.getTienDo());
			p.setUpdatedAt(projectDto.getUpdatedAt());
			
			ActivityLog activity = new ActivityLog();
			activity.setAction(projectDto.getDescription());
			activity.setStatus(projectDto.getStatus());
			activity.setProject(p);
			activity.setTimestamp(p.getUpdatedAt());
			
			projectRepository.save(p);
			activityRepository.save(activity);
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
		    Date timestamp = (tmp[3] != null) ? (Date) tmp[3] : null;
		    String status = (tmp[2] != null) ? (String) tmp[2] : null;
		    Integer userId = (tmp[4] != null) ? (Integer) tmp[4] : null;
		    Integer task = (tmp[5] != null) ? (Integer) tmp[5] : null;
		    activities.add(new ActivityDto(id, action, timestamp, userId, task, status));
		    
		}
		return activities;
	}

	@Override
	public Long getInProcessProjectsCount() {
		// TODO Auto-generated method stub
		return projectRepository.countInProcessProjects();
	}

	@Override
	public Long getCompletedProjectsCount() {
		// TODO Auto-generated method stub
		return projectRepository.countInCompletedProjects();
	}

	@Override
	public Long getCancelProjectsCount() {
		// TODO Auto-generated method stub
		return projectRepository.countInCancelProjects();
	}

	@Override
	public List<Project> searchProject(String key) {
		return projectRepository.searchProject(key);
	}
	
	
	

}
