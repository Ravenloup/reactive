package com.noone.reactive;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.noone.reactive.dbService.UserCrudRepository;
import com.noone.reactive.entiry.User;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableReactiveMongoRepositories
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
public class ReactiveApplication  extends AbstractReactiveMongoConfiguration {

	
	private static final Logger LOG = LoggerFactory.getLogger(ReactiveApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ReactiveApplication.class, args);
		
		/*Flux<Integer> j = Flux.just(1, 2, 3, 4, 5);

		j.map(i -> i * 10)
		  .subscribe(System.out::println);

		j.map(i -> i + 5)
		  .subscribe(System.out::println);*///reactive stream
		
		/*Stream<Integer> j1 = Arrays.asList(1, 2, 3, 4, 5).stream();

		j1.map(i -> i * 10)
		  .forEach(System.out::println);

		j1.map(i -> i + 5)
		  .forEach(System.out::println);*///java 8 stream will fail
	}
	@Override
	@Bean
	@DependsOn("embeddedMongoServer")
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(String.format("mongodb://localhost:%d", 27017));
    }
	@Override
	protected String getDatabaseName() {
		return "reactive-mongo";
	}

}
