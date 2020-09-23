package com.springboot.rest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document(collection = "employees")
public class Employee {

    @Id
    private String _id;

    @NotNull(message = "firstname must not be null")
    private String firstname;

    @NotNull(message = "lastname must not be null")
    private String lastname;

    @NotNull(message = "email must not be null")
    private String email;

}
