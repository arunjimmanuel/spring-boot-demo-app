package com.arun.immanuel.jobtracker.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfiguration {

    @Value("${solr.client.url}")
    private String solrHost;

    @Bean
    public SolrClient solrClient() {
        return new Http2SolrClient.Builder(solrHost).build();
    }
}