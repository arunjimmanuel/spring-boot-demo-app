package com.arun.immanuel.jobtracker.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.arun.immanuel.jobtracker.entity.Job;

public interface JobRepository extends MongoRepository<Job, String> {
}
