package com.web.webservice.dto;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TaskDto {
	private Integer taskId;
    private String taskName;
    private String description;
    private Date dueDate;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Integer assigneeId;
    private Integer projectId;
	
}
