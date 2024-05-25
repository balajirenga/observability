package com.homeobserver.framework.core.controller;

import java.nio.ByteBuffer;

import org.apache.http.entity.ByteArrayEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.homeobserver.framework.core.app.RestTemplateConfig;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.proto.collector.trace.v1.ExportTraceServiceRequest;
import io.opentelemetry.sdk.resources.Resource;

@Component
public class BalajiRestClient {

    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    
    private RestTemplate restTemplate;

    // @Autowired
    // RestTemplateConfig restTemplateConfig;

    public BalajiRestClient() {
      // restTemplate = restTemplateConfig.restTemplate(restTemplateBuilder);
      // System.out.println("BalajiRestClient is created " + restTemplate + " " + restTemplateConfig);
      RestTemplateConfig restTemplateConfig = new RestTemplateConfig();

      RestTemplateBuilder restTemplateBuilder = restTemplateConfig.restTemplateBuilder();
      restTemplate = restTemplateConfig.restTemplate(restTemplateBuilder);
    }

    public void makeAuthenticatedRequest(byte[] jsonBytes, String cloudId) {
        //String otLPEndPoint = "https://612dfe4aee8b4a39813fefc77144d97e.apm.us-central1.gcp.cloud.es.io:443";
       String otLPEndPoint = "http://localhost:8080/justReceiveData";
		String bearerToken = "gy328vOqmYYjWLMkrY";
		System.out.println("OTLP URL " + otLPEndPoint);
		System.out.println("OTLP Token " + bearerToken);

        // Set the Authorization header with the bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken); // Set bearer token
       // headers.setContentType(MediaType.parseMediaType("application/x-protobuf"));
        headers.set("cloudId", cloudId);
        headers.set("service.name", "ManualPostService");
        // ...

        headers.set("deployment.environment", "Balaji_Home");

        // GlobalOpenTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), headers, HttpHeadersSetter.INSTANCE);

        // Wrap the byte array in a ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(jsonBytes);


          try {

                // Create an HttpEntity with the headers
                HttpEntity<byte[]> entity = new HttpEntity<>(jsonBytes, headers);

                // Make the HTTP request with the specified URL, headers, and HTTP method
                ResponseEntity<String> response = restTemplate.exchange(
                    otLPEndPoint,
                    HttpMethod.GET, // Change HttpMethod as needed (GET, POST, etc.)
                    entity,
                    String.class
                );

                // Process the response as needed
                System.out.println("BalajiRestClient || Response: " + response.getBody() + "response " + response.getStatusCode());
                            
            } catch (Exception e) {
                e.printStackTrace();

            }



    }


    public static void main(String[] args) {

        BalajiRestClient restClient = new BalajiRestClient();
        String url = "https://example.com/api/resource";
        String bearerToken = "your_bearer_token_here";

        // url = otLPEndPoint;
        // bearerToken = secretToken;
        // String url2 = "https://9bc5fbde732f4390a33fdcf47e5da760.us-central1.gcp.cloud.es.io:443";
        // String cloudId = "0abcc474143d4c76bf7a208bbe05a7ce:dXMtY2VudHJhbDEuZ2NwLmNsb3VkLmVzLmlvJDliYzVmYmRlNzMyZjQzOTBhMzNmZGNmNDdlNWRhNzYwJGU2MWM0ZDgxNTJkYjQxZjBiM2EyN2JmOGVhZjY2MDg1";
 
        // System.out.println("OTLP URL " + url);
		// System.out.println("OTLP Token " + bearerToken);
        
        // System.out.println("cloudId" + cloudId);
        // restClient.makeAuthenticatedRequest( "Hellow world", cloudId);

    }
}