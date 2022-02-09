package com.example.repositoryservice.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByName(String name);
    Mono<? extends Void> deleteById(User user);

    Mono<String> findFirstByName(String s);
}