package com.example.entityjparelation.uploadsummary;

import com.example.entityjparelation.template.TemplateTypeEntity;
import com.example.entityjparelation.uploadsummary.models.UploadSummaryEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UploadSummarySpecification {

    public static Specification<UploadSummaryEntity> buildSpecification(List<Map<String, Object>> filters, String filterCondition) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Predicate finalPredicate = null;
            // Predicate predicate = criteriaBuilder.conjunction();

            for (Map<String, Object> filter : filters) {
                String field = (String) filter.get("field");
                String operation = (String) filter.get("operation");
                Object value = filter.get("value");

                if (field != null && operation != null && value != null) {
                    Path<?> path;

                    if ("templateName".equals(field)) {
                        // Handle associated entity property
                        Join<UploadSummaryEntity, TemplateTypeEntity> join = root.join("templateTypeEntity");
                        path = join.get("templateName");
                    } else {
                        path = root.get(field);
                    }

                    Predicate predicate = null;

                    switch (operation) {
                        case "contains":
                            if (value instanceof String) {
                                predicate = criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%");
                            }
                            break;
                        case "startsWith":
                            if (value instanceof String) {
                                predicate = criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), value.toString().toLowerCase() + "%");
                            }
                            break;
                        case "endsWith":
                            if (value instanceof String) {
                                predicate = criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + value.toString().toLowerCase());
                            }
                            break;
                        case "equals":
                            if ("submissionDate".equals(field) && value instanceof String) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date parsedDate = sdf.parse(value.toString());
                                    predicate = criteriaBuilder.equal(criteriaBuilder.function("date", Date.class, path), parsedDate);
                                } catch (ParseException e) {
                                    // Handle parse exception
                                    e.printStackTrace();
                                }
                            } else if (value instanceof String) {
                                predicate = criteriaBuilder.equal(criteriaBuilder.lower(path.as(String.class)), value.toString().toLowerCase());
                            } else if (value instanceof Number) {
                                predicate = criteriaBuilder.equal(path, value);
                            }
                            break;
                        case "isAnyOf":
                            if (value instanceof List) {
                                predicate = path.in((List<?>) value);
                            }
                            break;
                        // Handle other cases...
                    }

                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
            }


            Predicate finalPredicate;

            if ("AND".equalsIgnoreCase(filterCondition)) {
                finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else if ("OR".equalsIgnoreCase(filterCondition)) {
                finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            } else {
                // Default to AND if filterCondition is not recognized
                finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            return finalPredicate;
        };
    }

    private static Predicate combinePredicates(Predicate predicate1, Predicate predicate2, String filterCondition, CriteriaBuilder criteriaBuilder) {
        if ("AND".equalsIgnoreCase(filterCondition)) {
            return criteriaBuilder.and(predicate1, predicate2);
        } else if ("OR".equalsIgnoreCase(filterCondition)) {
            return criteriaBuilder.or(predicate1, predicate2);
        } else {
            // Default to AND if filterCondition is not recognized
            return criteriaBuilder.and(predicate1, predicate2);
        }
    }
}

