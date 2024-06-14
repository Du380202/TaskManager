package com.webservice.TaskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webservice.TaskService.entity.ActivityLog;

public interface ActivityRepository extends JpaRepository<ActivityLog, Integer>{
	@Query(value="{CALL SP_Get_Activity(:taskId)}", nativeQuery = true)
	List<Object[]> findByTaskId(@Param("taskId") Integer taskId);
}
