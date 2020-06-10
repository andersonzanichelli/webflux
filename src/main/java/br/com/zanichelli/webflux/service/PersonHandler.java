package br.com.zanichelli.webflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import br.com.zanichelli.webflux.model.Person;
import br.com.zanichelli.webflux.repository.ReactivePersonRepository;
import reactor.core.publisher.Mono;

@Component
public class PersonHandler {

	@Autowired
	ReactivePersonRepository repository;
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repository.findAll(), Person.class);
	}
	
	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repository.findById(id), Person.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest request) {
		final Mono<Person> person = request.bodyToMono(Person.class);
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromPublisher(person.flatMap(repository::save), Person.class));
	}
	
	public Mono<ServerResponse> deleteById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repository.deleteById(id), Person.class);
	}
	
	public Mono<ServerResponse> findByfirstname(ServerRequest request) {
		String firstname = request.pathVariable("firstname");
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(repository.findByFirstName(firstname), Person.class);
	}
}
