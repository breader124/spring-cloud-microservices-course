package com.breader.springmicroservices.post;

import com.breader.springmicroservices.exception.UserNotFoundException;
import com.breader.springmicroservices.user.User;
import com.breader.springmicroservices.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Profile("mock")
@RequiredArgsConstructor
public class MockedPostService implements PostService {
    private static int postCount = 0;

    private final UserService userService;

    @Override
    public List<Post> findAllPosts(int userId) {
        Optional<User> optUser = userService.findUser(userId);
        return optUser.map(User::getPostList).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<Post> findPost(int userId, int postId) {
        Optional<User> optUser = userService.findUser(userId);
        if (optUser.isPresent()) {
            User u = optUser.get();
            List<Post> postList = u.getPostList();
            return postList.stream()
                    .filter(p -> p.getId().equals(postId))
                    .findFirst();
        }
        throw new UserNotFoundException();
    }

    @Override
    public Post savePost(int userId, Post post) {
        Optional<User> optUser = userService.findUser(userId);
        return optUser.map(user -> {
            if (post.getId() == null) {
                post.setId(++postCount);
                post.setUser(user);
                post.setPostedAt(new Date());

                user.setPostList(new ArrayList<>());
            }
            user.getPostList().add(post);
            return post;
        }).orElseThrow(UserNotFoundException::new);
    }
}
