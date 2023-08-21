package com.example.entityjparelation.uploadsummary;

import com.example.entityjparelation.base.CrdpUIException;
import com.example.entityjparelation.base.CrdpUIRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/upload")
public class UploadSummaryController {
    private final UploadSummaryService uploadSummaryService;

    @Autowired
    public UploadSummaryController(UploadSummaryService
                                           uploadSummaryService) {
        this.uploadSummaryService = uploadSummaryService;
    }

    @GetMapping(value = "/summary", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public CrdpUIRestResponse<Map<String, Object>> getUploadSummary(@RequestBody
                                                                    Map<String, Object> requestMap) {
        int pageNo = requestMap.get("pageNo") == null ? 1 : (int) requestMap.get("pageNo");
        int pageSize = requestMap.get("pageSize") == null ? 10 : (int) requestMap.get("pageSize");
        String sortBy = requestMap.get("sortBy") == null ? "batchId" : (String) requestMap.get("sortBy");
        String sortOrder = requestMap.get("sortOrder") == null ? "desc" : (String) requestMap.get("sortOrder");
        String filterCondition = requestMap.get("filterCondition") == null ? "AND" : (String) requestMap.get("filterCondition");
        List<Map<String, Object>> filters = (List<Map<String, Object>>) requestMap.get("filters");
        Map<String, Object> result = uploadSummaryService.getUploadSummary(pageNo, pageSize, sortBy,
                sortOrder, filterCondition, filters);

        try {
            if (null != result.get("content") && !((List<?>) result.get("content")).isEmpty()) {
                return new CrdpUIRestResponse<>(CrdpUIRestResponse.SUCCESS_CODE, CrdpUIRestResponse.SUCCESS_MESSAGE,
                        result);
            } else {
                return new CrdpUIRestResponse<>(CrdpUIRestResponse.ERROR_CODE, "No Results found!", result);
            }

        } catch (CrdpUIException e) {
            throw e;
        } catch (Exception e) {
            throw new CrdpUIException("Error while retrieving Upload Summary details - ", e);
        }
    }
}
