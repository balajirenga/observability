// SampleHttpClient.java
package com.home.opentel.opentelhome;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.httpclient.JavaHttpClientTelemetry;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;

import java.net.http.HttpClient;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class SampleHttpClient {
    //Init OpenTelemetry
    private static final OpenTelemetry openTelemetry = AutoConfiguredOpenTelemetrySdk.initialize().getOpenTelemetrySdk();

    //Use this HttpClient implementation for making standard http client calls.
    public HttpClient createTracedClient(OpenTelemetry openTelemetry) {
        return JavaHttpClientTelemetry.builder(openTelemetry).build().newHttpClient(createClient());
    }

    //your configuration of the Java HTTP Client goes here:
    private HttpClient createClient() {
        return HttpClient.newBuilder().build();
    }

    public static void main(String[] args) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(System.getenv().getOrDefault("EXTERNAL_API_ENDPOINT", "http://localhost:8080/rolldice")))
            //.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
            .build();

        SampleHttpClient s = new SampleHttpClient();
        HttpResponse<String> response = s.createTracedClient(openTelemetry).send(request, HttpResponse.BodyHandlers.ofString());
        // print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
        // print status code
        System.out.println(response.statusCode());
        // print response body
        System.out.println(response.body());

    }
}
