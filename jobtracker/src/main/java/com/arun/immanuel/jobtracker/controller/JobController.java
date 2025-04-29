package com.arun.immanuel.jobtracker.controller;

import org.springframework.web.bind.annotation.*;

import com.arun.immanuel.jobtracker.entity.Job;
import com.arun.immanuel.jobtracker.respository.JobRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Job> getJobById(@PathVariable String id) {
        return jobRepository.findById(id);
    }

    @PostMapping
    public Job addJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable String id, @RequestBody Job job) {
        job.setId(id);
        return jobRepository.save(job);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable String id) {
        jobRepository.deleteById(id);
    }
}
