package com.breader.springmicroservices.user;

import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
public class JpaUserService implements UserService {
    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public Optional<User> findUser(int id) {
        return Optional.empty();
    }

    @Override
    public void deleteUser(int id) {

    }
}
