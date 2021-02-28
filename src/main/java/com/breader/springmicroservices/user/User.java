package com.breader.springmicroservices.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Date birthDate;

    private List<Post> postList;
}
