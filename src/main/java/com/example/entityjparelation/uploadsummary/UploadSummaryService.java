package com.example.entityjparelation.uploadsummary;

import com.example.entityjparelation.uploadsummary.models.UploadSummaryDTO;
import com.example.entityjparelation.uploadsummary.models.UploadSummaryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadSummaryService {
    @Autowired
    private UploadSummaryRepository uploadSummaryRepository;

    @Autowired
    public UploadSummaryService(UploadSummaryRepository uploadSummaryRepository) {
        this.uploadSummaryRepository = uploadSummaryRepository;
    }

    public Map<String, Object> getUploadSummary(Integer pageNo,
                                                Integer pageSize,
                                                String sortBy,
                                                String sortOrder,
                                                List<Map<String, Object>> filters) {

        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        sortBy = sortBy == null ? "batchId" : sortBy;
        sortOrder = sortOrder == null ? "asc" : sortOrder;

        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }

        if (sortBy.equals("templateName")) {
            sortBy = "templateTypeEntity.templateName";
        }

        Sort sort = Sort.by(new Sort.Order(direction, sortBy));

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Specification<UploadSummaryEntity> spec = UploadSummarySpecification.buildSpecification(filters);
        Page<UploadSummaryDTO> pageResult = uploadSummaryRepository.findAll(spec, pageable).map(UploadSummaryTransform::entityToDto);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("content", pageResult.getContent());
        responseMap.put("pageable", pageResult.getPageable());

        return responseMap;
    }


}
