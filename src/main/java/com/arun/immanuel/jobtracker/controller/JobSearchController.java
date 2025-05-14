package com.arun.immanuel.jobtracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arun.immanuel.jobtracker.respository.solr.JobSolrRepository;
import com.arun.immanuel.jobtracker.solr.model.JobDocument;

@RestController
@RequestMapping("/api/jobs")
public class JobSearchController {

    @Autowired
    private JobSolrRepository jobSolrRepository;

    @GetMapping("/all")
    public List<JobDocument> getAllSortedByPostedDate() throws Exception {
        SolrQuery query = new SolrQuery("*:*");
        query.setSort("createdAt", SolrQuery.ORDER.desc);
        query.setRows(1000);

        return jobSolrRepository.search(query);
    }

    @GetMapping("/search")
    public List<JobDocument> searchJobs(
            @RequestParam(defaultValue = "*:*") String q,
            @RequestParam(required = false) Boolean isRemote,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "createdAt desc") String sort,
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "20") int rows) throws Exception {

        SolrQuery query = new SolrQuery();
        query.setQuery(q);
        query.setStart(start);
        query.setRows(rows);
        String[] sortParts = sort.trim().split("\\s+");
        if (sortParts.length == 2) {
            query.setSort(sortParts[0], SolrQuery.ORDER.valueOf(sortParts[1].toLowerCase()));
        } else {
            throw new IllegalArgumentException("Invalid sort format. Expected format: fieldName asc|desc");
        }

        // Filters
        List<String> filters = new ArrayList<>();
        if (isRemote != null)
            filters.add("isRemote:" + isRemote);
        if (companyName != null)
            filters.add("companyName:\"" + companyName + "\"");
        if (location != null)
            filters.add("location:\"" + location + "\"");

        for (String fq : filters) {
            query.addFilterQuery(fq);
        }

        return jobSolrRepository.search(query);
    }

}
