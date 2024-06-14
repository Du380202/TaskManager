package com.web.webservice.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser {
	private String userId;
	private String username;
    private String password;
    private String email;
    private String fullName;
    private String role;
    private Date createdAt;
    private String img;
}
