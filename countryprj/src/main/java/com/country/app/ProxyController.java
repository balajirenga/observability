package com.country.app;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/country")
public class ProxyController {

    private final RestTemplate restTemplate;

    public ProxyController() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/proxy")
    public ResponseEntity<String> proxyRequest(
            @RequestBody String requestBody,
            @RequestHeader HttpHeaders requestHeaders) {

        // Print incoming request headers and body
        System.out.println("Incoming Request Headers:");
        requestHeaders.forEach((header, values) -> System.out.println(header + ": " + values));
        System.out.println("Incoming Request Body:");
        System.out.println(requestBody);

        // Forward the request to the actual destination
        //String url = "https://destination-url.com"; // Replace with the actual destination URL
        String url = "https://612dfe4aee8b4a39813fefc77144d97e.apm.us-central1.gcp.cloud.es.io:443";
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(requestBody, requestHeaders),
                String.class);

        // Print the response status code and body
        System.out.println("Response Status Code: " + responseEntity.getStatusCode());
        System.out.println("Response Body:");
        System.out.println(responseEntity.getBody());

        // Return the response received from the destination
        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
    }
}