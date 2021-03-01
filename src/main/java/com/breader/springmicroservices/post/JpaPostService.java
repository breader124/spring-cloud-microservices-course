package com.breader.springmicroservices.post;

import java.util.List;
import java.util.Optional;

public class JpaPostService implements PostService {
    @Override
    public List<Post> findAllPosts(int userId) {
        return null;
    }

    @Override
    public Optional<Post> findPost(int userId, int postId) {
        return Optional.empty();
    }

    @Override
    public Post savePost(int userId, Post post) {
        return null;
    }
}
