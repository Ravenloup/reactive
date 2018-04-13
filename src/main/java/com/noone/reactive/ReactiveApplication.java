package com.noone.reactive;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableReactiveMongoRepositories
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
public class ReactiveApplication  extends AbstractReactiveMongoConfiguration {

	
	private static final Logger LOG = LoggerFactory.getLogger(ReactiveApplication.class);
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ReactiveApplication.class, args);
		
		Flux<Integer> j = Flux.just(1, 2, 3, 4, 5);
/*
		j.map(i -> i * 10)
		  .subscribe(System.out::println);

		j.map(i -> i + 5)
		  .subscribe(System.out::println);//reactive stream
		
		j = Flux.merge(j, Flux.just(6));*/
		
		/*Stream<Integer> j1 = Arrays.asList(1, 2, 3, 4, 5).stream();

		j1.map(i -> i * 10)
		  .forEach(System.out::println);

		j1.map(i -> i + 5)
		  .forEach(System.out::println);*///java 8 stream will fail
//		Reactive Example
//		ReactiveApplication example = new ReactiveApplication();
//        example.getMessagesAsStream().subscribe(req -> System.out.println("req = " + req));
//        example.getMessagesAsStream().subscribe(msg -> System.out.println(msg.toUpperCase()));
//        example.handleMessage("een");
//        example.handleMessage("twee");
//        example.handleMessage("drie");
//        example.addMessage("sunil");
		
		/*ReactorClientHttpConnector reactorClient = new ReactorClientHttpConnector();
		WebClient.create().get().uri("/messages").exchange().subscribe(req -> System.out.println("req = " + req));*/
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
	
	private List<FluxSink<String>> handlers = new ArrayList<>();

    public Flux<String> getMessagesAsStream() {
        Flux<String> result = Flux.create(sink -> {
                handlers.add(sink);
            sink.onCancel(() -> handlers.remove(sink));
        });

        return result;
    }

    public void handleMessage(String message) {
        handlers.forEach(han -> han.next(message));
    }

    public void addMessage(String message) throws InterruptedException {
    	Thread.sleep(10000);
    	handleMessage(message);
    }
}
