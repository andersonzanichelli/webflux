package br.com.zanichelli.webflux.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zanichelli.webflux.model.Person;
import br.com.zanichelli.webflux.repository.ReactivePersonRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/person")
public class PersonController {

	private ReactivePersonRepository reactiveRepository;
	
	public PersonController(ReactivePersonRepository reactiveRepository) {
	    this.reactiveRepository = reactiveRepository;
	}
	
	@GetMapping("/")
	public Flux<Person> findAll() {
		return reactiveRepository.findAll();
	}
	
	@GetMapping("/firstname/{firstname}")
	public Flux<Person> findByFirstname(@PathVariable String firstname) {
		return reactiveRepository.findByFirstName(firstname);
	}
	
	@GetMapping(value = "/{id}")
	public Mono<Person> findById(@PathVariable String id) {
		return reactiveRepository.findById(id);
	}
	
	@PostMapping("/")
	public Mono<Person> save(@RequestBody Person person) {
		return reactiveRepository.save(person);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable String id) {
		return reactiveRepository.deleteById(id);
	}
	
	@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Tuple2<Long, Person>> getStreamEvents() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
		Flux<Person> all = reactiveRepository.findAll();
		
		return Flux.zip(interval, all);
	}
}
