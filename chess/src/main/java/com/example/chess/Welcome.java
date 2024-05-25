package com.example.chess;

import org.springframework.web.bind.annotation.RestController;

import io.micrometer.observation.annotation.Observed;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class Welcome {

   
    @RequestMapping("/home")
    @Observed (name="welcome")
    public String welcome(){
        System.out.println("In the welcome method +++ .. " + Thread.currentThread().getName());
        return "Hi there, welcome to my world...";
    }
    
    
}
