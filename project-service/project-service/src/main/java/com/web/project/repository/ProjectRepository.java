package com.web.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.project.entity.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	@Query(value="{CALL SP_CountProject}", nativeQuery = true)
	public String countProject();
	
	@Query(value="{CALL SP_LayDuAn}", nativeQuery = true)
	List<Object[]> getProjects();
	
	@Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'In process'")
    Long countInProcessProjects();
	
	@Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'Completed'")
    Long countInCompletedProjects();
	@Query("SELECT COUNT(p) FROM Project p WHERE p.status = 'Cancelled'")
    Long countInCancelProjects();
	
	@Query("select p from Project p Where p.projectName like %?1%")
	List<Project> searchProject(String key);
	
}
