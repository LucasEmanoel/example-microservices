package br.com.ossav.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Foo Bar")
@RestController
@RequestMapping("book-service")
public class FooBarController{
	
	private static final Logger logger = LoggerFactory.getLogger(FooBarController.class);
	
	@Operation(summary = "Foo Bar")
	@GetMapping(value = "/foo-bar")
	//@Retry(name = "default", fallbackMethod = "fallbackMethod")
	//@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
	@Bulkhead(name = "default")
	public String fooBar() {
		//nao ta chamando fall
		//Cannot invoke "org.slf4j.Logger.info(String)" because "this.logger" is null
		logger.info("Request foo-bar Received");
		/*var response = new RestTemplate()
				.getForEntity("http://localhost:8080/foo-bar", String.class);*/
		//return response.getBody();
		return "ok";
	}
	
	public String fallbackMethod(Exception ex) {
		return "fallbackMethod foo-bar";
	}
}
