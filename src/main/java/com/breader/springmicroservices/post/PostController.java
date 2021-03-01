package com.breader.springmicroservices.post;

import com.breader.springmicroservices.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "users/{id}/posts")
    public List<EntityModel<Post>> getAllPostsForUser(@PathVariable int id) {
        List<Post> postList = postService.findAllPosts(id);
        List<EntityModel<Post>> resourceList = new LinkedList<>();

        postList.forEach(post -> {
            EntityModel<Post> resource = EntityModel.of(post);
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getPost(id, post.getId()));
            resource.add(linkTo.withRel("post-info"));
            resourceList.add(resource);
        });

        return resourceList;
    }

    @GetMapping("users/{userId}/posts/{postId}")
    public EntityModel<Post> getPost(@PathVariable int userId, @PathVariable int postId) {
        return postService.findPost(userId, postId)
                .map(post -> {
                    EntityModel<Post> resource = EntityModel.of(post);
                    WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllPostsForUser(userId));
                    resource.add(linkTo.withRel("all-posts"));
                    return resource;
                })
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
