package com.adeva.task.domain;

import com.adeva.task.constants.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

/**
 * Generic object representing application's custom REST response.
 */
@JsonPropertyOrder({"status_code", "status", "message", "data"})
public class RestResponse {

    @JsonProperty("status_code")
    private int statusCode;
    private Status status;
    private Object data;

    @JsonInclude(Include.NON_NULL)
    private String message; // Optional: will be included in JSON only in case of not null.

    public RestResponse(HttpStatus statusCode, Status status, Object data) {
        this(statusCode, status, null, data);
    }

    public RestResponse(HttpStatus statusCode, Status status, String message, Object data) {
        this.statusCode = statusCode.value();
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Status getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
