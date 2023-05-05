package br.com.ossav.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OpenApiConfiguration {

	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apis(
			SwaggerUiConfigParameters config,
			RouteDefinitionLocator locator){
		
		var definitions = locator.getRouteDefinitions().collectList().block();
		
		definitions.stream().filter(
				routeDefinition -> routeDefinition.getId()
					.matches(".*-service")).forEach(routeDefinition -> {
						String nome = routeDefinition.getId();
						config.addGroup(nome);
						GroupedOpenApi.builder()
							.pathsToMatch("/" + nome + "/**")
							.group(nome).build();
					});
		
		return new ArrayList<>();
	}
}
