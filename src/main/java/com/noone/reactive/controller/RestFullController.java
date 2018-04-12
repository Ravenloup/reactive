/**
 * 
 */
package com.noone.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.noone.reactive.entiry.User;
import com.noone.reactive.handler.RequestHandlerFunction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author sunil.yadav
 *
 */
@RestController
public class RestFullController {

	@GetMapping("/messages")
    Flux<String> allMessages(){
        return Flux.just(
            new StringBuilder().append("Sunil ").toString(),
            new StringBuilder().append("Yadav").toString()
        );
    }
	
	@GetMapping("/message")
    Mono<String> message(){
        return Mono.just(
            new StringBuilder().append("yadav").toString()
        );
    }
	
	@GetMapping("/messages/{id}")
	Mono<String> findById(@PathVariable String id) {
		return Mono.just(
	            new StringBuilder().append("message id = ").append(id).toString()
		        );
	}
	@Autowired
	RequestHandlerFunction handlerFunction;
	HandlerFunction<ServerResponse> helloWorld =
			  request -> ServerResponse.ok().syncBody(("Hello World"));
	@Bean
	public RouterFunction<ServerResponse> routes() {
		User user = new User();
		user.setFirstName("Sunil");
		user.setLastName("Yadav");
		user.setAge(26);
	  return
	  		RouterFunctions.
	  		        route(
	  		        		RequestPredicates.GET("/"), helloWorld)
	  		      .andRoute(
	  		        		RequestPredicates.GET("/user"),
	  		            request -> ServerResponse.ok().body(Mono.just(user), User.class))
	  		    .andRoute(
  		        		RequestPredicates.GET("/get-user"), handlerFunction::getUsers)
	  		        .andRoute(
	  		        		RequestPredicates.GET("/{name}"),
	  		            request -> ServerResponse.ok().body(Mono.just("hello, " + request.pathVariable("name") + "!"), String.class))
	
	  		        .andRoute(
	  		        		RequestPredicates.GET("/**"),
	  		            request -> ServerResponse.ok().body(Mono.just("fallback"), String.class))
	  		      .andRoute(
	  		        		RequestPredicates.POST("/user").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), 
	  		        				handlerFunction::createUser)
	  		        ;
	  }
}
