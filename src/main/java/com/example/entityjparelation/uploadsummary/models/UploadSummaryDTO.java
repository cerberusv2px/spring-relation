package com.example.entityjparelation.uploadsummary.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadSummaryDTO implements Serializable {
    private Long batchId;
    private String templateName;
    private String fileName;
    private String submissionDate;
    private String submittedBy;
    private String uploadStatus;
    private String jobStatus;
    private String uploadComments;
}
