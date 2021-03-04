package com.breader.springmicroservices.post;

import com.breader.springmicroservices.exception.UserNotFoundException;
import com.breader.springmicroservices.user.User;
import com.breader.springmicroservices.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Profile("jpa")
@Transactional
@RequiredArgsConstructor
public class JpaPostService implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public List<Post> findAllPosts(int userId) {
        return postRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Post> findPost(int userId, int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Post savePost(int userId, Post post) {
        Optional<User> optUser = userRepository.findById(userId);
        return optUser.map(user -> {
            post.setUser(user);
            post.setPostedAt(new Date());
            return postRepository.save(post);
        }).orElseThrow(UserNotFoundException::new);
    }
}
