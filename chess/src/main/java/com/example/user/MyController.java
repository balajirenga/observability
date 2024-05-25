package com.example.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MyController {

    private final MyUserService myUserService;

    MyController(MyUserService myUserService){
        this.myUserService = myUserService;
    }

    @GetMapping("/user/{userId}")
    String userName(@PathVariable("userId") String userId) {
        System.out.println("inside the method Mycontroller.username");
        return myUserService.userName(userId);
    }

    @RequestMapping("/hello")
    String hello(){
        System.out.println("Hello here..");
        return "hello here!";
    }
}