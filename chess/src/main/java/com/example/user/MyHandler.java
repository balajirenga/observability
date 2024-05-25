package com.example.user;



import org.springframework.stereotype.Component;


import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;

@Component
public class MyHandler implements ObservationHandler<Observation.Context>{
    
    @Override
    public void onStart(Observation.Context context) {
        System.out.println("Insider the onstart method");
    }

    @Override
    public void onStop(Observation.Context context) {
        System.out.println("Insider the onstop method");
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
       //return true;
       throw new UnsupportedOperationException();
    }

   /*  private String getUserTypeFromContext(Observation.Context context) {
        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(),false)
                    .filter(keyValue -> "userType".equals(keyValue.getKey()))
                   .map(KeyValue::getValue)
                    .findFirst()
                     .orElse("UNKNOWN");   
    } */   
}
