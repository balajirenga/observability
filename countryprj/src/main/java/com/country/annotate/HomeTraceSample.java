package com.country.annotate;

public class HomeTraceSample {
 
    public static void main(String[] args) {
        HomeTraceSample sample = new HomeTraceSample();
        sample.doPizza("");    
    }


    @HomeTraceCollector(setSomething = "Balaji", spanName = "PreparePizza")
    public String doPizza(String abc){
        System.out.println("I am here in the pizza method.. ");
        return abc;
    }


}
