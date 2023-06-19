package com.RP.user.service.reposotries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RP.user.service.entities.User;

public interface UserReposotries extends JpaRepository<User, String>{

	// if you want to add any custom query
}
