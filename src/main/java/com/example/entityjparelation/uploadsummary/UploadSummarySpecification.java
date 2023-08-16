package com.example.entityjparelation.uploadsummary;

import com.example.entityjparelation.uploadsummary.models.UploadSummaryEntity;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public class UploadSummarySpecification {

    public static Specification<UploadSummaryEntity> buildSpecification(List<Map<String, Object>> filters) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            for (Map<String, Object> filter : filters) {
                String field = (String) filter.get("field");
                String operation = (String) filter.get("operation");
                Object value = filter.get("value");

                if (field != null && operation != null && value != null) {
                    Path<?> path = root.get(field);

                  switch (operation) {
                        case "contains":
                            if (value instanceof String) {
                                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%"));
                            }
                            break;
                        case "startsWith":
                            if (value instanceof String) {
                                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), value.toString().toLowerCase() + "%"));
                            }
                            break;
                        case "endsWith":
                            if (value instanceof String) {
                                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + value.toString().toLowerCase()));
                            }
                            break;
                        case "equals":
                            if (value instanceof String) {
                                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(path.as(String.class)), value.toString().toLowerCase()));
                            } else if (value instanceof Number) {
                                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(path, value));
                            } else if (value instanceof Date) {
                                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(path, value));
                            }
                            break;
                        case "isAnyOf":
                            if (value instanceof List) {
                                predicate = criteriaBuilder.and(predicate, path.in((List<?>) value));
                            }
                            break;
                        // Handle additional operations here
                    }
                }
                }
            }

            return predicate;
        };
    }
}
