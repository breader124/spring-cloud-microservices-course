package com.breader.springmicroservices.user;

import com.breader.springmicroservices.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoService {
    private static final List<User> userList = new ArrayList<>();
    private static int userCount = 3;

    static {
        userList.add(new User(1, "Adam", new Date(), new ArrayList<>()));
        userList.add(new User(2, "Eve", new Date(), new ArrayList<>()));
        userList.add(new User(3, "Jack", new Date(), new ArrayList<>()));
    }

    public List<User> findAllUsers() {
        return userList;
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }
        userList.add(user);
        return user;
    }

    public Optional<User> findUser(int id) {
        return userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public void deleteUser(int id) {
        Optional<User> optUser = findUser(id);
        optUser.ifPresentOrElse(userList::remove, () -> {
            throw new UserNotFoundException();
        });
    }

    public List<Post> findAllPosts(int userId) {
        Optional<User> optUser = findUser(userId);
        return optUser.map(User::getPostList).orElseThrow(UserNotFoundException::new);
    }

    public Optional<Post> findPost(int userId, int postId) {
        Optional<User> optUser = findUser(userId);
        if (optUser.isPresent()) {
            User u = optUser.get();
            List<Post> postList = u.getPostList();
            return postList.stream()
                    .filter(p -> p.getId().equals(postId))
                    .findFirst();
        }
        throw new UserNotFoundException();
    }

    public Post savePost(int userId, Post post) {
        Optional<User> optUser = findUser(userId);
        return optUser.map(user -> {
            if (post.getId() == null) {
                post.setId(++userCount);
                post.setUserId(userId);
                post.setPostedAt(new Date());

                user.setPostList(new ArrayList<>());
            }
            user.getPostList().add(post);
            return post;
        }).orElseThrow(UserNotFoundException::new);
    }
}
