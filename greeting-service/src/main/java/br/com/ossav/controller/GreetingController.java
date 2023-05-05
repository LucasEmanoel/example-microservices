package br.com.ossav.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ossav.configuration.GreetingConfiguration;
import br.com.ossav.model.Greeting;

@RestController
public class GreetingController {

	private static final String template = "%s, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	private GreetingConfiguration greetingConfiguration;
	
	@RequestMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value="name",
			defaultValue = "") String name) {
		if(name.isEmpty()) name = greetingConfiguration.getDefaultValue();
		return new Greeting(
					counter.incrementAndGet(),
					String.format(template, greetingConfiguration.getGreeting(), name)
				);
	}
}
