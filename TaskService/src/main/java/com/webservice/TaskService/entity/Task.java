package com.webservice.TaskService.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
    private Integer taskId;

    @Column(nullable = false)
    private String taskName;

    @Lob
    private String description;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
    
    @Column(name="assignee_id")
    private Integer assigneeId;
    
    @Column(name="project_id")
    private Integer projectId;
    
    

    // Constructors, getters, setters, etc.
}