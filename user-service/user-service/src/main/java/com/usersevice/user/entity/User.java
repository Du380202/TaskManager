package com.usersevice.user.entity;

import lombok.*;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
    private Integer userId;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @Column(nullable = false, unique = true, name="email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(nullable = false, name="role")
    private String role;

    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "avatar")
    private String img;
}