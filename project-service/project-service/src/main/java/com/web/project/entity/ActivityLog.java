package com.web.project.entity;

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

    @Column(name = "timestamp")
    private Date timestamp;
    @Column(name="user_id")
    private Integer userId;
    @Column(name="status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}