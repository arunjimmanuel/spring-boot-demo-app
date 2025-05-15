package com.arun.immanuel.jobtracker.cronjob;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.arun.immanuel.jobtracker.dto.JobApiResponse;
import com.arun.immanuel.jobtracker.mapper.JobApiToSolrMapper;
import com.arun.immanuel.jobtracker.service.JobServiceImpl;
import com.arun.immanuel.jobtracker.solr.model.JobDocument;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobFetchService {
    private final JobServiceImpl jobService;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://www.arbeitnow.com/api/job-board-api?page=";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Runs every 2 hours AFTER previous completion
    @Scheduled(fixedDelay = 2 * 60 * 60 * 1000) // every 2 hours
    public void fetchAndInsertJobsToSolr() {

        log.info("Starting scheduled job fetch at {}", LocalDateTime.now().format(FORMATTER));

        for (int page = 1; page <= 2; page++) {
            try {
                String url = API_URL + page;
                ResponseEntity<JobApiResponse> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<JobApiResponse>() {
                        });

                List<JobApiResponse.Job> jobs = response.getBody().getData();
                List<JobDocument> jobDocs = jobs.stream()
                        .map(JobApiToSolrMapper::map)
                        .toList();
                log.info("Saving {} jobs to Solr...", jobDocs.size());
                jobService.saveMultipleJobsToSolr(jobDocs);
            } catch (Exception e) {
                log.error("Error fetching page {}: {}", page, e.getMessage());
            }
        }
        log.info("Completed scheduled job fetch at {}", LocalDateTime.now().format(FORMATTER));
    }
}
