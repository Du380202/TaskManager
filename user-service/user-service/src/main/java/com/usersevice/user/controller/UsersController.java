package com.usersevice.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersevice.user.dto.UserCreateDto;
import com.usersevice.user.entity.User;
import com.usersevice.user.model.ResponseObject;
import com.usersevice.user.service.UsersService;

import jakarta.ws.rs.DELETE;

@RestController
@RequestMapping("/api/user")
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@GetMapping("/userId/{id}")
	public ResponseEntity<ResponseObject> getUserById(@PathVariable Integer id) {
		User user = usersService.findById(id).get();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "Lấy dữ liệu thành công", user));
	}
	
	@GetMapping("/fullName/{id}")
	public ResponseEntity<ResponseObject> getFullName(@PathVariable Integer id) {
		User user = usersService.findById(id).get();
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "Lấy dữ liệu thành công", user.getFullName()));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<ResponseObject> getUserByUsername(@PathVariable String username) {
		User user = usersService.findByUsername(username);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "Lấy dữ liệu thành công", user));
	}
	
	@GetMapping("/All")
	public ResponseEntity<ResponseObject> getUserByRole() {
		
		try {
			List<User> users = usersService.findByRole();
			return ResponseEntity.status(HttpStatus.OK).body(new 
					ResponseObject(HttpStatus.OK.value(), "Lấy dữ liệu thành công", users));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Dữ liệu không hợp lệ", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Đã xảy ra lỗi", e.getMessage())
            );
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<ResponseObject> createUser(@RequestBody UserCreateDto user) {
		System.out.println(user.getFullName() + user.getImg());
		try {
			boolean newUser = usersService.createUser(user);
			return ResponseEntity.status(HttpStatus.OK).body(new 
					ResponseObject(HttpStatus.OK.value(), "Lấy dữ liệu thành công", newUser));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Dữ liệu không hợp lệ", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Đã xảy ra lỗi", e.getMessage())
            );
        }
	}
	
	@PostMapping("/update/{userId}") 
	public ResponseEntity<ResponseObject> updateUser(@PathVariable Integer userId, @RequestBody UserCreateDto user) {
		
		try {
			boolean update = usersService.updateUser(userId, user);
			
			return ResponseEntity.status(HttpStatus.OK).body(new 
					ResponseObject(HttpStatus.OK.value(), "Lấy dữ liệu thành công", update));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Dữ liệu không hợp lệ", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Đã xảy ra lỗi", e.getMessage())
            );
        }
	}
	
	@PostMapping("/chance/{userId}") 
	public ResponseEntity<ResponseObject> chancePassword(@PathVariable Integer userId, @RequestBody String password) {
		
		try {
			boolean update = usersService.chancePassword(userId, password);
			
			return ResponseEntity.status(HttpStatus.OK).body(new 
					ResponseObject(HttpStatus.OK.value(), "Lấy dữ liệu thành công", update));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Dữ liệu không hợp lệ", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Đã xảy ra lỗi", e.getMessage())
            );
        }
	}
	
	@DeleteMapping("/delete/{userId}") 
	public ResponseEntity<ResponseObject> delete(@PathVariable Integer userId) {
		
		try {
			usersService.deleteUserById(userId);
			
			return ResponseEntity.status(HttpStatus.OK).body(new 
					ResponseObject(HttpStatus.OK.value(), "Đã xóa thành công", true));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Dữ liệu không hợp lệ", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Đã xảy ra lỗi", e.getMessage())
            );
        }
	}
	


}
