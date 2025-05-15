package com.arun.immanuel.jobtracker.utils;

public final class AppConstants {

    public static final long JWT_EXPIRATION_TIME = 10 * 60 * 1000; // 10 minutes

    public static final class ResponseKey {
        public static final String TIMESTAMP = "timestamp";
        public static final String STATUS = "status";
        public static final String ERROR = "error";
        public static final String MESSAGE = "message";
        public static final String PATH = "path";
        public static final String TOKEN = "token";
    }

    public static final class Solr {
        public static final String JOB_CORE = "jobcore";
    }

    public static final class Mongo {
        public static final String DB_NAME = "jobtrackerdb";
    }

    private AppConstants() {
    }
}
