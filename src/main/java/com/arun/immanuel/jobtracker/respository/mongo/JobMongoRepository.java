package com.arun.immanuel.jobtracker.respository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.arun.immanuel.jobtracker.entity.Job;

public interface JobMongoRepository extends MongoRepository<Job, String> {
}
