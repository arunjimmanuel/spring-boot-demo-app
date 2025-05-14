package com.arun.immanuel.jobtracker.respository.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.arun.immanuel.jobtracker.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

}
