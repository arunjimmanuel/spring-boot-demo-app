package com.arun.immanuel.jobtracker.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class JobApiResponse {
    private List<Job> data;

    @Data
    public static class Job {
        private String slug;
        private String title;

        @JsonProperty("company_name")
        private String companyName;

        private String location;
        private String description;

        @JsonProperty("url")
        private String url;

        @JsonProperty("remote")
        private Boolean isRemote;

        @JsonProperty("created_at")
        private String createdAt;

        private List<String> tags;

    }
}