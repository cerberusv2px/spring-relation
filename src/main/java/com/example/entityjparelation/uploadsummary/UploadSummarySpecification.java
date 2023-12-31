package com.example.entityjparelation.uploadsummary;

import com.example.entityjparelation.template.TemplateTypeEntity;
import com.example.entityjparelation.uploadsummary.models.UploadSummaryEntity;
import jakarta.persistence.criteria.*;
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
                                    Expression<Date> truncatedDate = criteriaBuilder.function("TRUNC", Date.class, path);
                                    predicate = criteriaBuilder.equal(truncatedDate, parsedDate);
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
                        case "lessThan":
                            if ("submissionDate".equals(field) && value instanceof String) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date parsedDate = sdf.parse(value.toString());

                                    // Extract the date part from the timestamp
                                    Expression<Date> dateExpression = criteriaBuilder.function("TRUNC", Date.class, path);

                                    // Compare using less than
                                    predicate = criteriaBuilder.lessThan(dateExpression, parsedDate);
                                } catch (ParseException e) {
                                    // Handle parse exception
                                    e.printStackTrace();
                                }
                            }
                            break;

                        case "greaterThan":
                            if ("submissionDate".equals(field) && value instanceof String) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date parsedDate = sdf.parse(value.toString());

                                    // TRUNC function to extract the date part
                                    Expression<Date> truncatedDate = criteriaBuilder.function("TRUNC", Date.class, path);

                                    predicate = criteriaBuilder.greaterThan(truncatedDate, parsedDate);
                                } catch (ParseException e) {
                                    // Handle parse exception
                                    e.printStackTrace();
                                }
                            }
                            break;

                        case "between":
                            if ("submissionDate".equals(field) && value instanceof List && ((List<?>) value).size() == 2) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    List<?> dateRange = (List<?>) value;
                                    Date startDate = sdf.parse(dateRange.get(0).toString());
                                    Date endDate = sdf.parse(dateRange.get(1).toString());

                                    // TRUNC function to extract the date part
                                    Expression<Date> truncatedDate = criteriaBuilder.function("TRUNC", Date.class, path);

                                    predicate = criteriaBuilder.between(truncatedDate, startDate, endDate);
                                } catch (ParseException e) {
                                    // Handle parse exception
                                    e.printStackTrace();
                                }
                            }
                            break;
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

