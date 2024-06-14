package com.webservice.TaskService.entity;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Activity_Log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private Date timestamp;
    @Column(name="user_id")
    private Integer userId;
    @Column(name="status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;


}