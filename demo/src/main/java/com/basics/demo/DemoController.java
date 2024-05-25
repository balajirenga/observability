package com.basics.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo")
public class DemoController {
    
    @GetMapping("/show")
    public String doDemo(){
        System.out.println("doing the demo here.. ");
        return "done";
    }

    @PostMapping("/post/{params}")
    public String doPostDemo(@PathVariable String params){
        System.out.println("doing the demo here.. " + params);
        return "done";
    }

}
