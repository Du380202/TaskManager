package com.usersevice.user.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usersevice.user.dto.UserCreateDto;
import com.usersevice.user.entity.User;
import com.usersevice.user.repository.UsersRepository;
import com.usersevice.user.service.UsersService;

import jakarta.transaction.Transactional;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public Optional<User> findById(Integer userId) {
		
		return usersRepository.findById(userId);
	}
	
	@Override
	@Transactional
	public boolean createUser(UserCreateDto user) {
		try {
			User newUser = new User();
			newUser.setUsername(user.getUsername());
			newUser.setEmail(user.getEmail());
			newUser.setFullName(user.getFullName());
			newUser.setCreatedAt(user.getCreatedAt());
			//Mã hóa
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			newUser.setRole("user");
			System.out.println(user.getImg());
			newUser.setImg(user.getImg());
			usersRepository.save(newUser);
			return true;
		} catch (Exception ex) {
			throw new RuntimeException("Error while creating new user: " + ex.getMessage(), ex);
		}
		
	}

	@Override
	public boolean updateUser(Integer userId, UserCreateDto user) {
		try {
			Optional<User> tmp = findById(userId);
			User getUser = tmp.get();
			getUser.setEmail(user.getEmail());
			getUser.setFullName(user.getFullName());
			// Ma hoa
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
			getUser.setPassword(passwordEncoder.encode(user.getPassword()));
			getUser.setRole("user");
			usersRepository.save(getUser);
			return true;
		} catch(Exception ex) {
			throw new RuntimeException("Error while creating new user: " + ex.getMessage(), ex);
		}
		
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return usersRepository.findByUsername(username);
	}

	@Override
	public List<User> findByRole() {
		try {
			List<User> result = new ArrayList<User>();
			for(User user : usersRepository.findByRole()) {
				result.add(new User(user.getUserId(), user.getUsername(),user.getPassword() , user.getEmail(), user.getFullName(),user.getRole(), user.getCreatedAt(),null));
			}
			return result;			
		} catch (Exception e) {
			throw new RuntimeException("Error while creating new user: " + e.getMessage(), e);
		}
	}

	@Override
	public boolean chancePassword(Integer userId, String password) {
		Optional<User> tmp = findById(userId);
		User getUser = tmp.get();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		getUser.setPassword(passwordEncoder.encode(password));
		usersRepository.save(getUser);
		return true;
	}

	@Override
	public void deleteUserById(Integer id) {
		if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
	}
	

}
