package com.noone.reactive;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.noone.reactive.dbService.UserCrudRepository;
import com.noone.reactive.entiry.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveApplicationTests {

	@Autowired
    private UserCrudRepository repository;
    @Autowired
    private ReactiveMongoOperations operations;
 
    @Before
    public void setUp() {
        @SuppressWarnings("deprecation")
		CollectionOptions collectionOptions = new CollectionOptions(1048576L, 10L, true);
		operations.collectionExists(User.class)
        .flatMap(exists -> exists ? operations.dropCollection(User.class) : Mono.just(exists))
        .flatMap(o -> operations.createCollection(User.class, collectionOptions ))
        .then()
        .block();
        repository
        .saveAll(Flux.just(new User("sunil", "yadav", 26),
                new User("sunil", "yadav", 26),
                new User("sunil", "yadav", 26),
                new User("sunil1", "yadav", 26)))
        		.then()
        		.block();
    }
 
    @Test
    public void findByNameWithMonoQueryTest() {
        List<User> tShirts = repository.findByFirstName("sunil")
                .collectList()
                .block();
        assertThat(tShirts).hasSize(3);
    }
}
