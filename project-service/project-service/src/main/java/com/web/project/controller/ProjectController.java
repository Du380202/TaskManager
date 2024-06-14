package com.web.project.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.project.dto.ProjectDto;
import com.web.project.entity.Project;
import com.web.project.model.ResponseObject;
import com.web.project.service.ProjectService;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	@GetMapping("")
	public ResponseEntity<ResponseObject> getAll() {
		
		try {
			List<Project> listProject =  projectService.findAll();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", listProject));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra", ex.getMessage()));
	    }
		
	}
	
	@GetMapping("/searchProject")
	public ResponseEntity<ResponseObject> getSearch(@RequestParam("key") String key) {
		
		try {
			List<Project> listProject =  projectService.searchProject(key);
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", listProject));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra", ex.getMessage()));
	    }
		
	}
	
	@GetMapping("/detail")
	public ResponseEntity<ResponseObject> getById(@RequestParam("projectId") Integer taskId) {
		
		try {
			Project task = projectService.findById(taskId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", task));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	@GetMapping("/count")
	public String countProject(Model model) {
		return projectService.countProject();
	}
	
	
	@PostMapping("/create")
    public ResponseEntity<ResponseObject> createProject(@RequestBody ProjectDto newProject) {
        try {
            boolean check = projectService.createNew(newProject);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject(HttpStatus.CREATED.value(), "Lưu dữ liệu thành công", check)
            );
        } catch (DataIntegrityViolationException e) {
            // Handle specific exceptions for better error handling
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Dữ liệu không hợp lệ", null)
            );
        } catch (Exception e) {
            // Log the exception (optional but recommended)
            // log.error("Error occurred while creating project", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Đã xảy ra lỗi", e.getMessage())
            );
        }
    }
	
	@PostMapping("/update/{id}")
	public ResponseEntity<ResponseObject> updateProject(@PathVariable Integer id,@RequestBody Project newProject){
		try {
			long millis = System.currentTimeMillis();
			newProject.setUpdatedAt(new Date(millis));
			boolean check = projectService.update(id, newProject);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(
					HttpStatus.CREATED.value(), "Lưu dữ liệu thành công", check));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject(
					HttpStatus.NO_CONTENT.value(), "Đã xảy ra lỗi", ex.getMessage()));
		
		}
	}
	
	@PostMapping("/updateProgress/{id}")
	public ResponseEntity<ResponseObject> update(@PathVariable Integer id,@RequestBody Project newProject){
		try {
			long millis = System.currentTimeMillis();
			newProject.setUpdatedAt(new Date(millis));
			boolean check = projectService.updateProject(id, newProject);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(
					HttpStatus.CREATED.value(), "Lưu dữ liệu thành công", check));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject(
					HttpStatus.NO_CONTENT.value(), "Đã xảy ra lỗi", ex.getMessage()));
		
		}
	}
	
	@GetMapping("/activity/{taskid}")
	public ResponseEntity<ResponseObject> getActivityByTaskId(@PathVariable Integer taskid) {
		try {
			List<com.web.project.dto.ActivityDto> activities = projectService.getActiviTyByTask(taskid);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", activities));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	@GetMapping("/countInprocess")
	public ResponseEntity<ResponseObject> getCountProcessc() {
		try {
			Long inProccess = projectService.getInProcessProjectsCount();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", inProccess));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	@GetMapping("/countCompleted")
	public ResponseEntity<ResponseObject> getCountCompleted() {
		try {
			Long inProccess = projectService.getCompletedProjectsCount();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", inProccess));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	@GetMapping("/countCancel")
	public ResponseEntity<ResponseObject> getCCaCompleted() {
		try {
			Long inProccess = projectService.getCancelProjectsCount();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", inProccess));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	
}
