package com.webservice.TaskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webservice.TaskService.entity.Task;
import com.webservice.TaskService.model.ResponseObject;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {
	List<Task> findAll();
	Task findById(int id);
	@Query(value="{CALL GetTasksByAssignee(:userId)}", nativeQuery = true)
    List<Task> getByUser(@Param("userId") Integer userId);
	
	@Query(value="{CALL GetTasksByProject(:projectId)}", nativeQuery = true)
    List<Task> getTaskByProject(@Param("projectId") Integer projectId);
	
	@Query("SELECT COUNT(p) FROM Task p WHERE p.status = 'In process'")
	Long countInProcessProjects();
	
	@Query("SELECT COUNT(p) FROM Task p WHERE p.status = 'Completed'")
	Long countCompletedProjects();
	
	@Query("SELECT COUNT(p) FROM Task p WHERE p.status = 'Cancelled'")
	Long countCancelProjects();
	
	@Query("select p from Task p Where p.taskName like %?1%")
	List<Task> searchTask(String key);
}
