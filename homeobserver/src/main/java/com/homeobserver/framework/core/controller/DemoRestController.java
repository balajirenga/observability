package com.homeobserver.framework.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.homeobserver.framework.core.dto.OutputResponseInfo;
import com.homeobserver.framework.core.dto.InputRequestInfo;
import com.homeobserver.framework.core.aspect.EnableRestCallLogs;

@RestController
public class DemoRestController {

    // use the custom annotation EnableRestLogs to enable logging
    @EnableRestCallLogs(valuesString = "testGetEndPoint")
    @GetMapping("/testGet/{name}")
    @ResponseStatus(HttpStatus.OK)
    public OutputResponseInfo testGetEndPoint(@PathVariable String name) {
        System.out.println("In the Get method..");
        OutputResponseInfo result=new OutputResponseInfo();
        result.setResponse_state(HttpStatus.OK);
        Map<String, Object> hm= new HashMap<>();
        hm.put("Id", Integer.valueOf(1));
        hm.put("Input_Name", String.valueOf(name));
        result.setPayload(hm);

        // if(name.equals("Balaji")) {
        //     throw new RuntimeException("Invalid Name");
        // }
        System.out.println("In the Get method (end)..");
        return result;
    }


   @PostMapping("/testPost")
   @ResponseStatus(HttpStatus.CREATED) 
   @EnableRestCallLogs
   public OutputResponseInfo testPostEndPoint(@RequestBody InputRequestInfo inputRequestInfo) { 
            OutputResponseInfo result=new OutputResponseInfo();
        result.setResponse_state(HttpStatus.CREATED);
            Map<String, Object> hm= new HashMap<>();
            hm.put("Payload", inputRequestInfo);
        result.setPayload(hm);
            return result; 
    }
}