package com.homeobserver.framework.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.homeobserver.framework.core.aspect.EnableRestCallLogs;
import com.homeobserver.framework.core.aspect.EnableHomeObserver;
import com.homeobserver.framework.core.dto.OutputResponseInfo;
import com.homeobserver.framework.core.service.MathsService;

import jakarta.websocket.server.PathParam;
import kotlin.ParameterName;

@RestController
public class MathFunctionController {
 
    @Autowired
    MathsService mathService;


    @EnableHomeObserver(contextName = "MathFunctionController")
    @GetMapping("/mathfunction/add/{a}/{b}")
    @ResponseStatus(HttpStatus.OK)
    public OutputResponseInfo doSum(@PathVariable int a, @PathVariable int b) {
        //System.out.println("In the Get method..");

        // AnotherSimpleProgram simple = new AnotherSimpleProgram();
        // simple.doObserve();
        int resultNum = mathService.anotherSum(a, b);


        OutputResponseInfo result=new OutputResponseInfo();
        result.setResponse_state(HttpStatus.OK);

        Map<String, Object> hm= new HashMap<>();
        hm.put("Id", Integer.valueOf(1));
        hm.put("Result", String.valueOf(resultNum));
        result.setPayload(hm);


      //  System.out.println("In the Get method (end)..");
        return result;
    }


  }
