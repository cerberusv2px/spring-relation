package com.example.entityjparelation.uploadsummary;

import com.example.entityjparelation.uploadsummary.models.UploadSummaryDTO;
import com.example.entityjparelation.uploadsummary.models.UploadSummaryEntity;

import java.text.SimpleDateFormat;

public class UploadSummaryTransform {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static UploadSummaryDTO entityToDto(UploadSummaryEntity entity) {
        UploadSummaryDTO dto = new UploadSummaryDTO();
        dto.setBatchId(entity.getBatchId());
        dto.setTemplateName(entity.getTemplateTypeEntity().getTemplateName());
        dto.setFileName(entity.getFileName());
        dto.setSubmissionDate(DATE_FORMAT.format(entity.getSubmissionDate()));
        dto.setSubmittedBy(entity.getSubmittedBy());
        dto.setUploadStatus(entity.getUploadStatus());
        dto.setJobStatus(entity.getJobStatus());
        dto.setUploadComments(entity.getUploadComments());
        return dto;
    }
}
