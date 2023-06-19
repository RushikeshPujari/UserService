package com.RP.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.RP.user.service.entities.Hotel;
import com.RP.user.service.entities.Rating;
import com.RP.user.service.entities.User;
import com.RP.user.service.exception.ResourceNotFound;
import com.RP.user.service.reposotries.UserReposotries;
import com.RP.user.service.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {

	@Autowired
	private UserReposotries reposotries;

	@Autowired
	private RestTemplate restTemplate;
	// private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	// static int count =1;
	@Override
	public User saveUser(User user) {
		String generatedID = UUID.randomUUID().toString();
		// String generatedID = Integer.toString(count);
		user.setUserId(generatedID);
		// count++;

		reposotries.save(user);
		return user;
	}

	@Override
	public List<User> getAllUser() {

		List<User> allUsers = reposotries.findAll();

		// System.out.println(allUsers);
		/*
		 * for (User user : allUsers) { //System.out.println(user.getUserId()); Rating[]
		 * ratingOfUser = restTemplate.getForObject(
		 * "http://localhost:8084/ratings/users/"+user.getUserId(),Rating[].class);
		 * List<Rating> ratings = Arrays.stream(ratingOfUser).toList();
		 * 
		 * for (Rating rating : ratings) { ResponseEntity<Hotel> forEntity =
		 * restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId()
		 * , Hotel.class); Hotel hotel = forEntity.getBody();
		 * System.out.println(hotel.getName()); }
		 * 
		 * 
		 * List<Rating> ratingList = ratings.stream().map(rating -> { //API call to
		 * hotel Service to get hotel ResponseEntity<Hotel> forEntity =
		 * restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId()
		 * , Hotel.class); Hotel hotel = forEntity.getBody(); //Set the hotel to rating
		 * rating.setHotel(hotel); //return rating return rating;
		 * }).collect(Collectors.toList());
		 * 
		 * user.setRatings(ratingList);
		 * 
		 * 
		 * }
		 */
		return allUsers;
	}

	@Override
	public User getUser(String userId) {

		// Get user from database with help of user Repositories
		User user = reposotries.findById(userId).orElseThrow(
				() -> new ResourceNotFound("User with Given ID is not Found in server --> ID : " + userId));

		// fetch rating of the above user from RATING SERVICE
		// http://localhost:8084/ratings/users/276a109c-49c1-42ff-8410-32bd03b7fdb9

		Rating[] ratingOfUser = restTemplate.getForObject("http://localhost:8084/ratings/users/" + user.getUserId(),
				Rating[].class);

		List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

		List<Rating> ratingList = ratings.stream().map(rating -> {
			// API call to hotel Service to get hotel
			ResponseEntity<Hotel> forEntity = restTemplate
					.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
			// Set the hotel to rating
			rating.setHotel(hotel);
			// return rating
			return rating;
		}).collect(Collectors.toList());

		user.setRatings(ratingList);

		return user;
	}

}
