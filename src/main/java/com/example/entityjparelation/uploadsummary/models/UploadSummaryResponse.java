package com.example.entityjparelation.uploadsummary.models;

public interface UploadSummaryResponse {

    long getBatchId();
    String getTemplateName();
    String getFileName();
    String getSubmissionDate();
    String getSubmittedBy();
    String getUploadStatus();
    String getJobStatus();
    String uploadComments();
}
