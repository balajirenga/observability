package com.homeobserver.framework.core.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.homeobserver.framework.core.controller.HttpServletRequestTextMapGetter;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;


@Aspect
@Component
public class BalajiObserveFilter implements Filter {

    // private Tracer tracer1;
    // private TextMapPropagator propagator;

    // @Autowired
    // private Tracer tracer;

    @Autowired
    HomeObserveConfiguration homeObserveConfiguration;

    public BalajiObserveFilter() {
        // this.tracer1 = tracer;
        // this.propagator =
        // GlobalOpenTelemetry.getPropagators().getTextMapPropagator();
    }

    // @Override
    // public void doFilter(ServletRequest request, ServletResponse response,
    // FilterChain chain)
    // throws IOException, ServletException {
    // Tracer tracer = GlobalOpenTelemetry.getTracer("ROOT_TRACER");

    // jakarta.servlet.http.HttpServletRequest httpServletRequest =
    // (jakarta.servlet.http.HttpServletRequest) request;
    // String url = httpServletRequest.getRequestURI();

    // Context extractedContext =
    // GlobalOpenTelemetry.getPropagators().getTextMapPropagator()
    // .extract(Context.current(), httpServletRequest,
    // HttpServletRequestTextMapGetter.INSTANCE);

    // httpServletRequest.getHeaderNames().asIterator().forEachRemaining(header -> {
    // System.out.println("Header Name: " + header);
    // System.out.println("Header Value: " + httpServletRequest.getHeader(header));
    // });

    // System.out.println("extracted Content " + extractedContext);
    // // Context extractedContext =
    // GlobalOpenTelemetry.getPropagators().getTextMapPropagator()
    // // .extract(Context.current(), (Map<String, String>) request, new
    // TextMapGetter<Map<String, String>>() {
    // // @Override
    // // public Iterable<String> keys(Map<String, String> carrier) {
    // // return carrier.keySet();
    // // }

    // // @Override
    // // public String get(Map<String, String> carrier, String key) {
    // // return carrier.get(key);
    // // }
    // // });

    // System.out.println("At the Filter Method : " + url);
    // Span span = tracer.spanBuilder("RootDefaultFilter[" + url + "]")
    // .setSpanKind(SpanKind.SERVER)
    // .setParent(extractedContext)
    // .startSpan();

    // try (Scope scope = span.makeCurrent()) {
    // chain.doFilter(request, response);
    // } catch (Exception e) {
    // span.setStatus(StatusCode.ERROR);
    // throw e;
    // } finally {
    // span.end();
    // System.out.println("End the Filter Method : " + url);
    // }
    // }

    // Second option on Filter..
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("BalajiObserveFilter: doFilter Method.... " + homeObserveConfiguration.tracer());
        // Tracer tracer = GlobalOpenTelemetry.getTracer("ROOT_TRACER");
        Tracer tracer = homeObserveConfiguration.tracer();
        TextMapPropagator propagator = GlobalOpenTelemetry.getPropagators().getTextMapPropagator();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        String traceId = httpServletRequest.getHeader("x-trace-id");

        httpServletRequest.getHeaderNames().asIterator().forEachRemaining(header -> {
            System.out.println("Header Name: " + header);
            System.out.println("Header Value: " + httpServletRequest.getHeader(header));
        });

         Context extractedContext =propagator.extract(Context.current(),
                httpServletRequest, HttpServletRequestTextMapGetter.INSTANCE);
        System.out.println("Extracted Content : " + extractedContext.toString());

    
        System.out.println("At the Filter Method : " + url);
        Span span = tracer.spanBuilder("HTTP Incoming Request RootDefaultFilter[" + url + "]")
                .setSpanKind(SpanKind.SERVER)
                .setParent(extractedContext)
                .startSpan();


        try (Scope scope = span.makeCurrent()) {
            System.out.println("Filter ---> Span created...." + span.getSpanContext().getSpanId() + " Trace ID: "
                    + span.getSpanContext().getTraceId());
            chain.doFilter(request, response);
        } catch (Exception e) {
            span.setStatus(StatusCode.ERROR);
            throw e;
        } finally {
            span.end();
            System.out.println("End the Filter Method : " + url);
        }
    }

    private Map<String, String> extractHeaders(HttpServletRequest httpServletRequest) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, httpServletRequest.getHeader(headerName));
        }
        return headers;
    }




    // other methods
}