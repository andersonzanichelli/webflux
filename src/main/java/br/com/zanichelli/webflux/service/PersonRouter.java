package br.com.zanichelli.webflux.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

//@Configuration
public class PersonRouter {
	
//	@Bean
	public RouterFunction<ServerResponse> route(PersonHandler handler) {
		return RouterFunctions
				.route(GET("/person").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
				.andRoute(GET("/person/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
				.andRoute(POST("/person").and(accept(MediaType.APPLICATION_JSON)), handler::save)
				.andRoute(GET("/person/firstname/{firstname}").and(accept(MediaType.APPLICATION_JSON)), handler::findByfirstname)
				.andRoute(DELETE("/person/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteById);
	}

}
