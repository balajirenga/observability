package com.homeobserver.framework.core.myaspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;

import com.homeobserver.framework.core.app.HomeObserveConfiguration;
import com.homeobserver.framework.core.aspect.Carrier;
import com.homeobserver.framework.core.handler.BalajiHandler;

import io.micrometer.observation.Observation;
import io.micrometer.observation.Observation.Context;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

import java.lang.System; // Import the System class

@Aspect
@Component
public class BalajiAspectImpl {
    
    @Autowired
    private Tracer tracer;



    @PostConstruct
    public void init() {
        System.out.println("BalajiAspectImpl: PostConstruct tracer is " + tracer);
    }

    public BalajiAspectImpl() {
        System.out.println("BalajiAspectImpl created");
        //tracer = GlobalOpenTelemetry.getTracer("BalajiAspectImpl");
    }

    @Autowired
    HomeObserveConfiguration homeObserveConfiguration;

    @Autowired
    BalajiHandler balajiHandler;

    @Around("@annotation(BalajiAspect)")
    public void helloWorld(ProceedingJoinPoint jointPoint) throws Throwable {
        // justTheBalajiOriginalWay(jointPoint);
        // springRecomendedWayForObservability(jointPoint);

        // The working way....
        Span span = createNewSpanAndClose(null, jointPoint);
        jointPoint.proceed();
        createNewSpanAndClose(span, jointPoint);
        // The working way....

        // Observation observation = tryOneMoreWay(null, jointPoint);
        // jointPoint.proceed();
        // tryOneMoreWay(observation, jointPoint);
    }

    /**
     * Spring recommended way
     * 
     * @param jointPoint
     * @throws Throwable
     */
    public void springRecomendedWayForObservability(ProceedingJoinPoint jointPoint) throws Throwable {
        System.out.println("BalajiAspectImpl: springRecomendedWayForObservability");
        String methodName = jointPoint.getSignature().getName();
        Observation observation = Observation.start(methodName, homeObserveConfiguration.createObservation());
        Observation.Scope scope = null;
        try {
            observation.contextualName(methodName);
            scope = observation.openScope();
            scope.makeCurrent();

            jointPoint.proceed();
        } catch (Exception e) {

        } finally {
            scope.close();
            observation.stop();
        }
    }

    /**
     * this is the method i have used to make it working most of the time.
     * 
     * @param jointPoint
     */
    public void justTheBalajiOriginalWay(ProceedingJoinPoint jointPoint) {
        System.out.println("BalajiAspectImpl: justTheBaljiOriginalWay");

        Object proceed = null;
        Observation observation = null;
        String methodName = jointPoint.getSignature().getName();

        System.out.println("Hello World " + methodName);
        try {
            observation = Observation.createNotStarted(methodName, homeObserveConfiguration.createObservation())
                    .start();
            observation.contextualName(methodName);
            // observation.start();
            observation.observe(() -> {
                try {
                    System.out.println("@ the core of the observe method... ==> methodName : " + methodName);

                    return jointPoint.proceed(); // Invoke the method dynamically
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return null; // Handle the exception gracefully
                }
            });
            // Context contextObj = observation.getContext();
            // balajiHandler.onStart(contextObj);
            // observation.stop();
            System.out.println("Set the Observe method.. for the method : " + methodName);
            proceed = jointPoint.proceed();

            // balajiHandler.onStop(contextObj);

            System.out.println("Completed the Observe method.. for the method : " + methodName);
        } catch (Exception e) {
            System.out.println("Exception occured : " + e.getMessage());
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public Span createNewSpanAndClose(Span span, ProceedingJoinPoint jointPoint) {
        System.out.println("BalajiAspectImpl: doSpider");
        String methodName = jointPoint.getSignature().getName();
        Span newSpan = null;
        if (span == null) {
            String url = myURLDetails(jointPoint);

            newSpan = tracer.spanBuilder(methodName + "[" + url + "]").startSpan();
            newSpan.setAttribute("ConqueredSpan", true);

      

            newSpan.setAttribute("URL", methodName + "[" + url + "]");

            newSpan.makeCurrent();    

            String spanId = newSpan.getSpanContext().getSpanId();
            String traceId = newSpan.getSpanContext().getTraceId();
      
            System.out.println("===> BalajiAspectImpl On Start: [" + methodName
            + "] TraceId : " + traceId + " Span ID:" + newSpan.getSpanContext().getSpanId() + "URL " + "");
    
        } else {
            span.end();
            System.out.println("===> BalajiAspectImpl On Start: [" + methodName
            + "] Span ended ");
        }
        return newSpan;
    }

    public String myURLDetails(JoinPoint joinPoint) {
        // Get method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        
        // Get method
        Method method = signature.getMethod();

        // Check if the method has @GetMapping annotation
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mappingAnnotation = method.getAnnotation(GetMapping.class);
            
            // Retrieve the path value from @GetMapping annotation
            String[] path = mappingAnnotation.value();
            System.out.println("Path: " + path[0]); // Assuming only one path is defined
            
            return path[0];
           
        }
        return "";
    
    }

    public Observation tryOneMoreWay(Observation observation, JoinPoint joinPoint){
        if(observation == null) {
            observation = Observation.createNotStarted("sample", homeObserveConfiguration.createObservation());
            observation.start();
            try (Observation.Scope scope2 = observation.openScope()) {

                String methodName = joinPoint.getSignature().getName();
                observation.contextualName(methodName);
                observation.contextualName(methodName);
  
                return observation;
            } catch (Exception e) {
                observation.error(e);
                // further exception handling
            } finally {
               // observation.stop();
            }
        } else {
            observation.stop();
        }
        return observation;
    }

}
