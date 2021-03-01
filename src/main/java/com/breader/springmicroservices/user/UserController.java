package com.breader.springmicroservices.user;

import com.breader.springmicroservices.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
public class UserController {
    private final UserService userService;

    @GetMapping("users")
    public List<EntityModel<User>> getAllUsers() {
        List<EntityModel<User>> userResourceList = new ArrayList<>();
        userService.findAllUsers().forEach(user -> {
            EntityModel<User> resource = EntityModel.of(user);
            WebMvcLinkBuilder linkToUser = linkTo(methodOn(this.getClass()).getUser(user.getId()));
            resource.add(linkToUser.withRel("user-info"));
            userResourceList.add(resource);
        });
        return userResourceList;
    }

    @GetMapping("users/{id}")
    public EntityModel<User> getUser(@PathVariable int id) {
        return userService.findUser(id)
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
        User newUser = userService.saveUser(u);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
