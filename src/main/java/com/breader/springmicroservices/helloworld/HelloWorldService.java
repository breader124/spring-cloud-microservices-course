package com.breader.springmicroservices.helloworld;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldService {
    @GetMapping("hello")
    public String helloWorld() {
        return "Hello World from Spring Boot project!";
    }

    @GetMapping("helloBean/{name}")
    public @ResponseBody HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
