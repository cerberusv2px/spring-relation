package com.example.entityjparelation.uploadsummary.models;

import com.example.entityjparelation.template.TemplateTypeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "UPLOAD_PROCESS_DETAILS")
public class UploadSummaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PROCESS_ID")
    private Long batchId;

    @Column(name = "S3_FILE_NAME")
    private String fileName;

    @Column(name = "SUBMISSION_DATE")
    private Date submissionDate;

    @Column(name = "SUBMITTED_BY")
    private String submittedBy;

    @Column(name = "UPLOAD_STATUS")
    private String uploadStatus;

    @Column(name = "JOB_STATUS")
    private String jobStatus;

    @Column(name = "UPLOAD_COMMENTS")
    private String uploadComments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    private TemplateTypeEntity templateTypeEntity;
}
