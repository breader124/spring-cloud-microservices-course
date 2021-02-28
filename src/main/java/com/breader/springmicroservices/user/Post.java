package com.breader.springmicroservices.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Post {
    private Integer id;
    private Integer userId;
    private Date postedAt;
    private String message;
}
