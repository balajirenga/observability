package com.movieapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class helloworld {
    
    @GetMapping("/messages") 
    public String hello(){
        return "Hello World";
    }
}
