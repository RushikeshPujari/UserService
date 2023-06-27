package com.RP.user.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.RP.user.service.controller.UserController;
import com.RP.user.service.entities.User;
import com.RP.user.service.services.UserServices;

public class testUserController {

	UserController userController = new UserController();
	
	UserServices userServices = mock(UserServices.class);
	
	public List<User> createMockuserData(){ 
	List<User> userList = new ArrayList<User>();
	User user = new User();
	user.setUserId("1U");
	user.setName("DummyUser");
	user.setEmail("DummyUser@co.in");
	user.setAbout("Developer");
	userList.add(user);
	return userList;
	}
	
	@Test
	void test_getAllUsers() {
		
		ReflectionTestUtils.setField(userController, "services", userServices);
		List<User> userList = createMockuserData();
		
		when(userServices.getAllUser()).thenReturn(userList);
		
		ResponseEntity<List<User>> response = userController.getAllUser();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userList, response.getBody());
	}
	
	@Test
	void test_getSingelUser() {
		
		ReflectionTestUtils.setField(userController, "services", userServices);
		List<User> userList = createMockuserData();
		String id = userList.get(0).getUserId();
		
		when(userServices.getUser(id)).thenReturn(userList.get(0));
	
		ResponseEntity<User> response = userController.getSingleUser(id);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userList.get(0), response.getBody());
	}
	
	@Test
	void test_PostCreateUser() {
		
		ReflectionTestUtils.setField(userController, "services", userServices);
		
		User input_user = new User("1U","DummyUser","DummyUser@co.in","Developer");
		
		User Created_user = new User("1U","DummyUser","DummyUser@co.in","Developer");
		when(userServices.saveUser(input_user)).thenReturn(Created_user);
		
		ResponseEntity<User> response = userController.createUser(input_user);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(Created_user, response.getBody());
		
		
	}
	
}
