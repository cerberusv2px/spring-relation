package com.example.entityjparelation.base;

import lombok.Builder;

@Builder
public class CrdpUIRestResponse<T> {

    public static final String SUCCESS_MESSAGE = "REST Successfully Executed and sent the response";
    public static final String ERROR_MESSAGE = "ERROR while executing the REST";
    public static final String SUCCESS_CODE = "SUCCESS";
    public static final String ERROR_CODE = "ERROR";

    private final String responseCode;
    private final String responseMessage;
    private T response;

    public CrdpUIRestResponse(final String responseCode, final String responseMessage, final T response) {
        this.responseCode = null == responseCode ? SUCCESS_CODE : responseCode;
        this.responseMessage = null == responseMessage ? SUCCESS_MESSAGE : responseMessage;
        this.response = null == response ? null : response;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

}
