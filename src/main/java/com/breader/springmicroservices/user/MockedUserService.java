package com.breader.springmicroservices.user;

import com.breader.springmicroservices.exception.UserNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Profile("mock")
public class MockedUserService implements UserService {
    private static final List<User> userList = new ArrayList<>();
    private static int userCount;

    static {
        userList.add(new User(1, "Adam", new Date(), new ArrayList<>()));
        userList.add(new User(2, "Eve", new Date(), new ArrayList<>()));
        userList.add(new User(3, "Jack", new Date(), new ArrayList<>()));

        userCount = userList.size();
    }

    @Override
    public List<User> findAllUsers() {
        return userList;
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }
        userList.add(user);
        return user;
    }

    @Override
    public Optional<User> findUser(int id) {
        return userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> optUser = findUser(id);
        optUser.ifPresentOrElse(userList::remove, () -> {
            throw new UserNotFoundException();
        });
    }
}
