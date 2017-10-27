package com.demo.controller;

import com.demo.controller.response.StandardResponse;
import com.demo.controller.response.Status;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("")
    public HttpEntity<StandardResponse<String>> index() {
        StandardResponse<String> response = new StandardResponse<String>(Status.SUCCESS)
            .payload("The application is up and running. ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
