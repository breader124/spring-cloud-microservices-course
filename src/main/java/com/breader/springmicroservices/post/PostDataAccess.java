package com.breader.springmicroservices.post;

import com.breader.springmicroservices.post.Post;

import java.util.List;
import java.util.Optional;

public interface PostDataAccess {
    List<Post> findAllPosts(int userId);
    Optional<Post> findPost(int userId, int postId);
    Post savePost(int userId, Post post);
}
