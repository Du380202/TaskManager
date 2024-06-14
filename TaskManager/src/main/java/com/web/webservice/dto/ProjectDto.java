package com.web.webservice.dto;

import java.sql.Date;

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
