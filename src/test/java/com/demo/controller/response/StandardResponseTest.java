package com.demo.controller.response;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StandardResponseTest {
    @Test
    public void constructor_whenStatusSuccess() {
        Object payload = new Object();

        StandardResponse<Object> standardResponse = new StandardResponse<>(Status.SUCCESS)
                .message("Success")
                .errors(null)
                .payload(payload);

        assertThat(standardResponse.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(standardResponse.getMessage()).isEqualTo("Success");
        assertThat(standardResponse.getErrors()).isNull();
        assertThat(standardResponse.getPayload()).isEqualTo(payload);
    }

    @Test
    public void constructor_whenStatusFailed() {
        List<String> errors = new ArrayList<>();

        StandardResponse<Object> standardResponse = new StandardResponse<>(Status.FAILED)
                .message("Failed")
                .errors(errors);

        assertThat(standardResponse.getStatus()).isEqualTo(Status.FAILED);
        assertThat(standardResponse.getMessage()).isEqualTo("Failed");
        assertThat(standardResponse.getErrors()).isEqualTo(errors);
        assertThat(standardResponse.getPayload()).isNull();
    }

    @Test
    public void constructor_whenStatusIsNull() {
        StandardResponse<Object> standardResponse = new StandardResponse<>(null)
                .message(null)
                .errors(null)
                .payload(null);

        assertThat(standardResponse.getStatus()).isNull();
        assertThat(standardResponse.getMessage()).isNull();
        assertThat(standardResponse.getErrors()).isNull();
        assertThat(standardResponse.getPayload()).isNull();
    }
}
