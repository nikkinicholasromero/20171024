package com.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
    @Id
    private String id;
    private String employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateLastUpdated;
}
