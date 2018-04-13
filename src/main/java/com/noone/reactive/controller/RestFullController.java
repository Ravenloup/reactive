/**
 * 
 */
package com.noone.reactive.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.noone.reactive.dbService.UserCrudRepository;
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


	@Autowired
	private RequestHandlerFunction handlerFunction;
	
	@Autowired
    private UserCrudRepository repository;
	HandlerFunction<ServerResponse> helloWorld =
			  request -> ServerResponse.ok().syncBody(("Hello World"));
			  
	@RequestMapping(value="/messages", produces= {MediaType.APPLICATION_JSON_VALUE})
    public Flux<User> allMessages(){
		/*List<User> tShirts = repository.findByFirstName("sunil")
                .collectList()
                .block();
		tShirts.forEach(System.out::println);*/
        return handlerFunction.listUsers();
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
	@Bean
	public RouterFunction<ServerResponse> routes() {
		User user = new User();
		user.setFirstName("Sunil");
		user.setLastName("Yadav");
		user.setAge(26);
	  return
	  		RouterFunctions.
	  		        route(
	  		        		RequestPredicates.GET("/helloword"), helloWorld)
	  		      .andRoute(
	  		        		RequestPredicates.GET("/user"),
	  		            request -> ServerResponse.ok().body(Mono.just(user), User.class))
	  		    .andRoute(
  		        		RequestPredicates.GET("/get-user"), handlerFunction::getUsers)
	  		    .andRoute(
	  		        		RequestPredicates.GET("/name/{name}"),
	  		            request -> ServerResponse.ok().body(Mono.just("hello, " + request.pathVariable("name") + "!"), String.class))
	
	  		        .andRoute(
	  		        		RequestPredicates.GET("/default/**"),
	  		            request -> ServerResponse.ok().body(Mono.just("fallback"), String.class))
	  		      .andRoute(
	  		        		RequestPredicates.POST("/user").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)), 
	  		        				handlerFunction::createUser)
	  		        ;
	  }
}
