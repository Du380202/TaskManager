package com.usersevice.user.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
	private Integer userId;
	private String username;
    private String password;
    private String email;
    private String fullName;
    private String role;
    private Date createdAt;
    private String img;
}
