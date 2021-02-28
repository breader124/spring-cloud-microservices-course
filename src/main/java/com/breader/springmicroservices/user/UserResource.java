package com.breader.springmicroservices.user;

import com.breader.springmicroservices.exception.PostNotFoundException;
import com.breader.springmicroservices.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class UserResource {
    private final UserDaoService userDaoService;

    @GetMapping("users")
    public List<EntityModel<User>> getAllUsers() {
        List<EntityModel<User>> userResourceList = new ArrayList<>();
        userDaoService.findAllUsers().forEach(user -> {
            EntityModel<User> resource = EntityModel.of(user);
            WebMvcLinkBuilder linkToUser = linkTo(methodOn(this.getClass()).getUser(user.getId()));
            resource.add(linkToUser.withRel("user-info"));
            userResourceList.add(resource);
        });
        return userResourceList;
    }

    @GetMapping("users/{id}")
    public EntityModel<User> getUser(@PathVariable int id) {
        return userDaoService.findUser(id)
                .map(user -> {
                    EntityModel<User> resource = EntityModel.of(user);
                    WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
                    resource.add(linkTo.withRel("all-users"));
                    return resource;
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("users")
    public ResponseEntity<URI> saveUser(@Valid @RequestBody User u) {
        User newUser = userDaoService.saveUser(u);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDaoService.deleteUser(id);
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
