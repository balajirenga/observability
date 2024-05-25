package com.country.Observation;

import io.micrometer.observation.ObservationRegistry;

public class BasicSpringSetup {
           
    public static ObservationRegistry registry = ObservationRegistry.create();

    public static void setup(){
        ObservationRegistry registry = ObservationRegistry.create();

        //registry.observationConfig().observationHandler(new CustomHandler());
    }
}