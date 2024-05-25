
package com.example.chess;

import io.micrometer.observation.Observation.Context;

import org.springframework.stereotype.Component;

import io.micrometer.observation.ObservationHandler;

@Component
public class SimpleLoggingHandler implements ObservationHandler<io.micrometer.observation.Observation.Context> {

    @Override
    public boolean supportsContext(Context arg0) {

        throw new UnsupportedOperationException("Unimplemented method 'supportsContext'");
    }

    @Override
    public void onStart(io.micrometer.observation.Observation.Context context){
           System.out.println("On the on-start method.."); 
           context.put("time", System.currentTimeMillis());
    }

    @Override
    public void onStop(io.micrometer.observation.Observation.Context context) {
        System.out.println("On the on-stop method");
        context.put("stop-time", System.currentTimeMillis());
    }

}