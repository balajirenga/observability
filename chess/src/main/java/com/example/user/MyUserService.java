package com.example.user;

import java.util.Random;

import org.springframework.stereotype.Service;

import io.micrometer.observation.annotation.Observed;

@Service
public class MyUserService {

    private final Random random = new Random();
    

    @Observed(name="user.name", contextualName="getting-user-name", lowCardinalityKeyValues = {"userType", "userType2"})
    public String userName(String userId) {
        System.out.println("Inside the Myuserservice.username method");

        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "foo";
    }

}
