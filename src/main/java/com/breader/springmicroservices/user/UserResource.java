package com.breader.springmicroservices.user;

import com.breader.springmicroservices.exception.PostNotFoundException;
import com.breader.springmicroservices.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserResource {
    private final UserDaoService userDaoService;

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return userDaoService.findOne(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("users")
    public ResponseEntity<URI> saveUser(@Valid @RequestBody User u) {
        User newUser = userDaoService.save(u);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDaoService.deleteOne(id);
    }

    @GetMapping("users/{id}/posts")
    public List<Post> getAllPostsForUser(@PathVariable int id) {
        return userDaoService.findAllPosts(id);
    }

    @GetMapping("users/{userId}/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable int userId, @PathVariable int postId) {
        return userDaoService.findPost(userId, postId)
                .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseThrow(PostNotFoundException::new);
    }

    @PostMapping("users/{userId}/posts")
    public ResponseEntity<URI> savePost(@PathVariable int userId, @RequestBody Post post) {
        Post newPost = userDaoService.savePost(userId, post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
