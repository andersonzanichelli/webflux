package br.com.zanichelli.webflux.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zanichelli.webflux.model.Person;
import reactor.core.publisher.Flux;

@Repository
public interface ReactivePersonRepository extends ReactiveCrudRepository<Person, String> {

	@Query("{ 'name' : { $regex: ?0 } }")
	Flux<Person> findByFirstName(String name);

}
