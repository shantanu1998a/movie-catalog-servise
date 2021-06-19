package in.nit.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import in.nit.moviecatalogservice.models.CatalogItem;
import in.nit.moviecatalogservice.models.Movie;
import in.nit.moviecatalogservice.models.Rating;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogIteam")
	public CatalogItem getCatalogItem(Rating rating) {
		// For each movie ID, call movie info service and get details
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
		// put them all together
		return new CatalogItem(movie.getName(), "Test", rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogIteam(Rating rating) {
		return new CatalogItem("Movie Note Found", "", rating.getRating());
	}
	
}
