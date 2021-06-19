package in.nit.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import in.nit.moviecatalogservice.models.Rating;
import in.nit.moviecatalogservice.models.UserRating;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserrating")
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/user/"+ userId, UserRating.class);
	}
	
	public UserRating getFallbackUserrating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserRating(Arrays.asList(
				new Rating("0",0)
		));
		return userRating;
	}
}
