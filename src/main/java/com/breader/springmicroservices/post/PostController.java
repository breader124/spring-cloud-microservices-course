package com.breader.springmicroservices.post;

import com.breader.springmicroservices.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "users/{id}/posts")
    public List<Post> getAllPostsForUser(@PathVariable int id) {
        return postService.findAllPosts(id);
    }

    @GetMapping("users/{userId}/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable int userId, @PathVariable int postId) {
        return postService.findPost(userId, postId)
                .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseThrow(PostNotFoundException::new);
    }

    @PostMapping("users/{userId}/posts")
    public ResponseEntity<URI> savePost(@PathVariable int userId, @RequestBody Post post) {
        Post newPost = postService.savePost(userId, post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
