package com.danielsp.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielsp.app.entity.User;
import com.danielsp.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//Create un nuevo usuario
	
	@PostMapping
	public ResponseEntity<?> create (@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
		
	}
	// Leer un ususario
	
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value = "id") Long userid){
		Optional<User> oUser = userService.findById(userid);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oUser);
		
	}
	
	//Actualizar un usuario
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update (@RequestBody User userDetails, @PathVariable(value = "id") Long userid){
		Optional<User> user = userService.findById(userid);
		
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails, user.get());
		user.get().setName(userDetails.getName());
		user.get().setSurname(userDetails.getSurname());
		user.get().setEmail(userDetails.getEmail());
		user.get().setEnabled(userDetails.getEnabled());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
	}
	
	//Borrar usuario
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete (@PathVariable(value = "id") Long userid){
		if(!userService.findById(userid).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		userService.deleteByid(userid);
		return ResponseEntity.ok().build();
		
	}
	
	//Read los usuarios
	
	@GetMapping
	public List<User> readAll (){
		
		List<User> users = StreamSupport.stream(userService.findAll().spliterator(), false).collect(Collectors.toList());
		
		return users;
	}

}
