package com.danielsp.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielsp.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

}
