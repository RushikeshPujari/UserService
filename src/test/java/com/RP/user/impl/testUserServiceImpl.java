package com.RP.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


import com.RP.user.service.entities.User;
import com.RP.user.service.exception.ResourceNotFound;
import com.RP.user.service.reposotries.UserReposotries;
import com.RP.user.service.services.impl.UserServiceImpl;


public class testUserServiceImpl {

	UserServiceImpl userServiceImpl = new UserServiceImpl();
	
	UserReposotries reposotrie = mock(UserReposotries.class);
	
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
	void test_getAllUser() {
		
		ReflectionTestUtils.setField(userServiceImpl, "reposotries", reposotrie);
		List<User> userList = createMockuserData();
		
		when(reposotrie.findAll()).thenReturn(userList);
		
		List<User> response = userServiceImpl.getAllUser();
		assertEquals(1, response.size());
	}
	
	@Test
	void test_create() {
		
		ReflectionTestUtils.setField(userServiceImpl, "reposotries", reposotrie);
		
		List<User> userList = createMockuserData();
		when(reposotrie.save(userList.get(0))).thenReturn(userList.get(0));
		
		User user = userServiceImpl.saveUser(userList.get(0));
		
		assertEquals("DummyUser", user.getName());
		
	}
	
	
	@Test
	void test_get_User_pass() {
		ReflectionTestUtils.setField(userServiceImpl, "reposotries", reposotrie);
		List<User> userList = createMockuserData();
		
		when(reposotrie.findById(userList.get(0).getUserId())).thenReturn(Optional.of(userList.get(0)));
		
		User response = userServiceImpl.getUser("1U");
		
		assertEquals("DummyUser", response.getName());
	}
	
	//Failing scenario
	@Test
	void test_get_fail() {
		ReflectionTestUtils.setField(userServiceImpl, "reposotries", reposotrie);

		ResourceNotFound thrown = Assertions.assertThrows(ResourceNotFound.class, () -> userServiceImpl.getUser("2"));
		assertEquals("User with Given ID is not Found in server --> ID : " + 2, thrown.getMessage());
				
			}

}
