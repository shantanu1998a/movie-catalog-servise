package in.nit.moviecatalogservice.resources;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import in.nit.moviecatalogservice.models.CatalogItem;
import in.nit.moviecatalogservice.models.Movie;
import in.nit.moviecatalogservice.models.Rating;
import in.nit.moviecatalogservice.models.UserRating;
import in.nit.moviecatalogservice.services.MovieInfo;
import in.nit.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private MovieInfo movieInfo;
	
	@Autowired
	private UserRatingInfo userRatingInfo; 
	
//	@Autowired
//	private WebClient.Builder builder;
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

//		WebClient.Builder builder = WebClient.builder();
		
		
		// get all rated movie IDs
//		List<Rating> ratings = Arrays.asList(
//				new Rating("1234", 4),
//				new Rating("5678", 5)
//		);
		
		UserRating ratings = userRatingInfo.getUserRating(userId);
		
		
		return ratings.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating))
		.collect(Collectors.toList());
		
	}
	
}



//Movie movie = builder.build()
//.get()
//.uri("http://localhost:8082/movies/"+rating.getMovieId())
//.retrieve()
//.bodyToMono(Movie.class)
//.block();
