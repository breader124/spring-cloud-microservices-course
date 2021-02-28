package com.breader.springmicroservices.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    @Size(min = 2)
    private String name;

    @Past
    private Date birthDate;

    private List<Post> postList;
}
