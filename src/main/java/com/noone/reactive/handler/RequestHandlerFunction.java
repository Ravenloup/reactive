/**
 * 
 */
package com.noone.reactive.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.noone.reactive.dbServiceImpl.UserRepository;
import com.noone.reactive.entiry.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author sunil.yadav
 *
 */
@Component
public class RequestHandlerFunction {
	
	@Autowired
	private UserRepository repository;
	
	public RequestHandlerFunction(UserRepository repository) {
		this.repository = repository;
	}
	public Mono<ServerResponse> createUser(ServerRequest request){
		Mono<User> user = request.bodyToMono(User.class);
		return ServerResponse.ok().body(Flux.merge((repository.createUser(user))), User.class);
	}
	
	public Mono<ServerResponse> getUsers(ServerRequest request){
		return ServerResponse.ok().body(Flux.merge((repository.getUsers())), User.class);
	}
	
}