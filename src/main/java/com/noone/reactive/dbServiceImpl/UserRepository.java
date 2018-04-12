/**
 * 
 */
package com.noone.reactive.dbServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.noone.reactive.dbService.UserInterface;
import com.noone.reactive.entiry.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author sunil.yadav
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private UserInterface userInterface;
	
	public Flux<User> createUser(Mono<User> user){
		return userInterface.insert(user);
	}
	
	public Flux<User> getUsers(){
		return userInterface.findAll();
	}
}
