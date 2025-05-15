package com.arun.immanuel.jobtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arun.immanuel.jobtracker.respository.solr.JobSolrRepository;
import com.arun.immanuel.jobtracker.solr.model.JobDocument;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobServiceImpl {

    @Autowired
    private JobSolrRepository jobSolrRepository;

    public void addJobToSolr(JobDocument jobDocument) {
        try {
            jobSolrRepository.save(jobDocument);
        } catch (Exception e) {
            log.error("Exception while saving the document to solr:", e);
        }
    }

    public void saveMultipleJobsToSolr(List<JobDocument> jobs) {
        try {
            jobSolrRepository.saveAll(jobs);
        } catch (Exception e) {
            log.error("Exception while saving multiple documents to solr:", e);
        }
    }

}
