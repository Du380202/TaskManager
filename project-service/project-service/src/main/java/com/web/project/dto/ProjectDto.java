package com.web.project.dto;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
	private Integer projectId;
	private String projectName;
	private String description;
	private Date startDate;
	private Date endDate;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private Integer manager_id;
	private Integer tienDo;
	
		
}	
