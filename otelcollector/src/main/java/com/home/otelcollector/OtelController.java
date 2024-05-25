package com.home.otelcollector;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collect")
public class OtelController {

    @PostMapping("/v1/traces")
    public String postObserveJSON(@RequestBody String data) {
        System.out.println("@ the /v1/traces --> postObserveJSON");
        return "Success";
    }


    @GetMapping("/cricket")
    public String checkme(){
       System.out.println("hello here in the check me mehtod..");
  
       // String jsonString = getStringFromObj(obj);
        String jsonString = "In the cricket ground.....";
        return jsonString; 
    }

    
    @PostMapping("/manual/v1/traces")
    public String submitToElastic(){
        return "Working on sending to elastic";
    }

}
