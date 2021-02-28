package com.breader.springmicroservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> userList = new ArrayList<>();

    static {
        userList.add(new User(1, "Adam", new Date()));
        userList.add(new User(2, "Eve", new Date()));
        userList.add(new User(3, "Jack", new Date()));
    }
}
