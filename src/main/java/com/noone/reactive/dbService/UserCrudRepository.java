/**
 * 
 */
package com.noone.reactive.dbService;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.noone.reactive.entiry.User;

import reactor.core.publisher.Flux;

/**
 * @author sunil.yadav
 *
 */
public interface UserCrudRepository extends ReactiveCrudRepository<User, String> {

	Flux<User> findByFirstName(String name);
	
}
