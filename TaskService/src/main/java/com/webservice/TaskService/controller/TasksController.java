package com.webservice.TaskService.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webservice.TaskService.dto.ActivityDto;
import com.webservice.TaskService.dto.TaskDetailDto;
import com.webservice.TaskService.entity.ActivityLog;
import com.webservice.TaskService.entity.Task;
import com.webservice.TaskService.model.ResponseObject;
import com.webservice.TaskService.service.*;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/task")
public class TasksController {
	@Autowired
	private TasksService taskService;
	
	@GetMapping("")
	public ResponseEntity<ResponseObject> getHelloword() {
		Optional<List<Task>> listCongViec = Optional.of(taskService.findAll());
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
				HttpStatus.OK.value(), "Lấy dữ liệu thành công", listCongViec));
	}
	
	@GetMapping("/details")
	public ResponseEntity<ResponseObject> getTaskDetail() {
		try {
			List<TaskDetailDto> tasks = taskService.getTaskDetail();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", tasks));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
	}
	
	@GetMapping("/searchTask")
	public ResponseEntity<ResponseObject> searchTask(@RequestParam("key") String key) {
		try {
			List<TaskDetailDto> tasks = taskService.searchTask(key);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", tasks));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
	}
	
	@GetMapping("/detail")
	public ResponseEntity<ResponseObject> getById(@RequestParam("taskId") Integer taskId) {
		
		try {
			Task task = taskService.findById(taskId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", task));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	@GetMapping("/taskByUser/{userId}")
	public ResponseEntity<ResponseObject> getTaskByUser(@PathVariable Integer userId) {
		try {
			List<Task> tasks = taskService.getTaskByUser(userId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", tasks));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
	}
	
	@GetMapping("/project/{projectId}")
	public ResponseEntity<ResponseObject> getTaskByProject(@PathVariable Integer projectId) {
		try {
			List<TaskDetailDto> tasks = taskService.getTaskByProjectId(projectId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", tasks));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	@GetMapping("/activity/{taskid}")
	public ResponseEntity<ResponseObject> getActivityByTaskId(@PathVariable Integer taskid) {
		try {
			List<ActivityDto> activities = taskService.getActiviTyByTask(taskid);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", activities));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<ResponseObject> createTask(@RequestBody Task newTask){
		try {
			newTask.setStatus("In process");
			boolean check = taskService.createNew(newTask);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(
					HttpStatus.CREATED.value(), "Lưu dữ liệu thành công", check));
		}
		catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject(
					HttpStatus.NO_CONTENT.value(), "Lưu dữ liệu thất bại", ex.getMessage()));
		
		}
	}
	
	@PostMapping("/update/{taskId}")
	public ResponseEntity<ResponseObject> updateTask(@PathVariable Integer taskId, @RequestBody Task newTask){
		try {
	        boolean updatedTask = taskService.updateTask(taskId, newTask);
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
	                HttpStatus.OK.value(), "Lưu dữ liệu thành công", updatedTask));
	    } catch (EntityNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
	                HttpStatus.NOT_FOUND.value(), "Không tìm thấy công việc với ID: " + taskId, null));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra", ex.getMessage()));
	    }
	}
	
	@PostMapping("/employee/update/{taskId}")
	public ResponseEntity<ResponseObject> updateTaskUser(@PathVariable Integer taskId, @RequestBody Task newTask){
		try {
	        boolean updatedTask = taskService.updateTaskUser(taskId, newTask);
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
	                HttpStatus.OK.value(), "Lưu dữ liệu thành công", updatedTask));
	    } catch (EntityNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
	                HttpStatus.NOT_FOUND.value(), "Không tìm thấy công việc với ID: " + taskId, null));
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Có lỗi xảy ra", ex.getMessage()));
	    }
	}
	
	@GetMapping("/countInprocess")
	public ResponseEntity<ResponseObject> getCountProcessc() {
		try {
			Long inProccess = taskService.getInProcessProjectsCount();
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
			Long inProccess = taskService.getCompletedProjectsCount();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", inProccess));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
	
	@GetMapping("/countCancel")
	public ResponseEntity<ResponseObject> getCountCancel() {
		try {
			Long inProccess = taskService.getCancelProjectsCount();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
					HttpStatus.OK.value(), "Lấy dữ liệu thành công", inProccess));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
            		HttpStatus.NOT_FOUND.value(), "Không có dữ liếu", ex.getMessage()));
        }
		
	}
}
