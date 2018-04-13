/**
 * 
 */
package com.noone.reactive.dbService;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.noone.reactive.entiry.User;

/**
 * @author sunil.yadav
 *
 */
@Service
public interface UserInterface extends ReactiveMongoRepository<User, String> {

	
}
