package com.usersevice.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usersevice.user.entity.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer>{
	@Query(value="{CALL GetUser(:username)}", nativeQuery = true)
	List<Object[]> getUser(@Param("username") String username);
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u")
    List<User> findByRole();
}
