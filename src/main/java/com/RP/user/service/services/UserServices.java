package com.RP.user.service.services;

import java.util.List;

import com.RP.user.service.entities.User;

public interface UserServices {
	
	//user operations
	
	//create
	User saveUser(User user);
	
	//list user
	List<User> getAllUser();
	
	//get single user 
	User getUser(String userId);

}
