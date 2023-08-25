package com.example.entityjparelation.uploadsummary;

import com.example.entityjparelation.uploadsummary.models.UploadSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadSummaryRepository extends JpaRepository<UploadSummaryEntity, Long> , JpaSpecificationExecutor<UploadSummaryEntity> {
}
