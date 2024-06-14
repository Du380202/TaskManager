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
public class TaskDetailDto {
	private Integer task_id;
	private Date created_at;
	private String status;
	private Date dueDate;
	private String taskName;
	private String full_name;
	
	
}
