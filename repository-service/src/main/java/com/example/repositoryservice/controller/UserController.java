package com.example.repositoryservice.controller;

import com.example.repositoryservice.r2dbc.User;
import com.example.repositoryservice.r2dbc.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private Environment env;

    @Autowired
    public UserController(UserRepository userRepository,
                          Environment env) {
        this.userRepository = userRepository;
        this.env = env;
    }


    //configuration 정보 가져오기
    @GetMapping("/health_check")
    public String status(){

        return String.format("It's Working in User Service"
                + ",port(local.server.port)=" + env.getProperty("local.server.port")
                + ",port(text.change)=" + env.getProperty("text.change"));
    }

    @GetMapping("/users")
    public Flux<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/user/{name}")
    public Mono<User> getUser(@PathVariable("name") String name) {
        return userRepository.findByName(name);
    }


    @PostMapping("/users/user")
    public Mono<User> saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }


    @DeleteMapping("/users/user/{name}")
    public Mono<Void> removeMember(@PathVariable String name) {
        return Mono.just(name)
                .flatMap(userRepository::findByName)
                .flatMap(userRepository::deleteById);
    }

    @PutMapping("/users/user/{name}")
    public Mono<User> updateUser(@PathVariable String name, @RequestParam String newName) {
        return Mono.just(name)
                .flatMap(userRepository::findFirstByName)
                .map(id -> User.updateUser(Long.valueOf(id), newName))
                .flatMap(userRepository::save);
    }


}
