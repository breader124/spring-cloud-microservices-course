package com.breader.springmicroservices.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    User saveUser(User user);
    Optional<User> findUser(int id);
    void deleteUser(int id);
}
