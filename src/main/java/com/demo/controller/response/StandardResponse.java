package com.demo.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponse<T> {
    private Status status;
    private String message;
    private T payload;
    private List<String> errors;

    public StandardResponse(Status status) {
        this.status = status;
    }

    public StandardResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public StandardResponse<T> payload(T payload) {
        this.payload = payload;
        return this;
    }

    public StandardResponse<T> errors(List<String> errors) {
        this.errors = errors;
        return this;
    }
}
