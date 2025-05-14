package com.arun.immanuel.jobtracker.solr.model;

import org.apache.solr.client.solrj.beans.Field;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JobDocument {

    @Field
    private String id;

    @Field
    private String title;

    @Field
    private String companyName;

    @Field
    private String location;

    @Field
    private String description;

    @Field
    private String url;

    @Field
    private Boolean isRemote;

    @Field
    private Date createdAt;

    @Field
    private List<String> tags;
}