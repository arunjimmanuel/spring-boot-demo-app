package com.arun.immanuel.jobtracker.mapper;

import java.time.Instant;
import java.util.Date;

import com.arun.immanuel.jobtracker.dto.JobApiResponse;
import com.arun.immanuel.jobtracker.solr.model.JobDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobApiToSolrMapper {

    public static JobDocument map(JobApiResponse.Job job) {
        JobDocument doc = new JobDocument();

        doc.setId(job.getSlug() + "-" +
                (job.getCompanyName() != null
                        ? job.getCompanyName().replaceAll("\\s+", "").toLowerCase()
                        : "unknown"));

        doc.setTitle(job.getTitle());
        doc.setCompanyName(job.getCompanyName());
        doc.setLocation(job.getLocation());
        doc.setDescription(job.getDescription());
        doc.setUrl(job.getUrl());
        doc.setIsRemote(job.getIsRemote());
        String createdAtStr = job.getCreatedAt();
        try {
            long epoch = Long.parseLong(createdAtStr);
            Date createdAtDate = Date.from(Instant.ofEpochSecond(epoch));
            doc.setCreatedAt(createdAtDate);
        } catch (NumberFormatException e) {
            log.error("Invalid timestamp {} for id {}: ", createdAtStr, job.getSlug());
            doc.setCreatedAt(null);
        }
        doc.setTags(job.getTags());

        return doc;
    }
}
