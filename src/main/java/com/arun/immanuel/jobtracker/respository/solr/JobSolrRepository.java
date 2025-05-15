package com.arun.immanuel.jobtracker.respository.solr;

import com.arun.immanuel.jobtracker.solr.model.JobDocument;
import com.arun.immanuel.jobtracker.utils.AppConstants;

import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobSolrRepository {

    private final SolrClient solrClient;
    private final String collection = AppConstants.Solr.JOB_CORE;

    public void save(JobDocument job) throws Exception {
        SolrInputDocument doc = new DocumentObjectBinder().toSolrInputDocument(job);
        solrClient.add(collection, doc);
        solrClient.commit(collection);
    }

    public void saveAll(List<JobDocument> jobs) throws Exception {
        DocumentObjectBinder binder = new DocumentObjectBinder();
        List<SolrInputDocument> docs = jobs.stream()
                .map(binder::toSolrInputDocument)
                .toList();
        UpdateRequest request = new UpdateRequest();
        request.add(docs);
        request.commit(solrClient, collection);
    }

    public List<JobDocument> search(SolrQuery query) throws SolrServerException, IOException {
        QueryResponse response = solrClient.query(collection, query);
        return response.getBeans(JobDocument.class);
    }

}