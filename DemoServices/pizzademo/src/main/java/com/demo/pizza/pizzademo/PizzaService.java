package com.demo.pizza.pizzademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.observecore.coreobserver.myaspect.ObserveDataVessel;
import com.observecore.coreobserver.rest.CoreObserveRestClient;

@Component
public class PizzaService {

    @Autowired
    CoreObserveRestClient coreobserverObj;

    @ObserveDataVessel(contextName = "Pizza Service | TotalMenu", transactionIdString = "x-transaction-id")
    public String getCompleteMenu(){
        String pizzString = getPizzaMenu();
        String cheeseString = getCheeseMenu();
        String ovenString = getOvenMenu();
        String packagingString = getPackagingMenu();
        String toppingString = getToppingMenu();

        String result = pizzString + "\n" + cheeseString + "\n" + ovenString + "\n" + packagingString + "\n" + toppingString;
        return result;
    } 

    @ObserveDataVessel(contextName = "Pizza Service | justPizzaMenu", transactionIdString = "x-transaction-id")
    public String getPizzaMenu() {
        String menu = "Pizza Menu: Cheese, Pepperoni, Sausage, Veggie, Meat Lovers, Supreme, BBQ Chicken, Hawaiian, Margherita, Buffalo Chicken, White Pizza, Spinach Alfredo, Taco Pizza, Philly Cheesesteak, Mac and Cheese Pizza, Breakfast Pizza";
    
        return menu;
    }


    @ObserveDataVessel(contextName = "Pizza Service | justCheeseMenu", transactionIdString = "x-transaction-id")
    public String getCheeseMenu(){
        String otLPEndPoint = "http://localhost:8501/demo/cheese/getMenu";

        String response = coreobserverObj.makeAuthenticatedRequest(otLPEndPoint, "".getBytes(), "");
        return response;
    }


    @ObserveDataVessel(contextName = "Pizza Service | justOvenMenu", transactionIdString = "x-transaction-id")
    public String getOvenMenu(){
        String otLPEndPoint = "http://localhost:8502/demo/oven/getMenu";

        String response = coreobserverObj.makeAuthenticatedRequest(otLPEndPoint, "".getBytes(), "");
        return response;
    }

    @ObserveDataVessel(contextName = "Pizza Service | justPackagingMenu", transactionIdString = "x-transaction-id")
    public String getPackagingMenu(){
        String otLPEndPoint = "http://localhost:8504/demo/packaging/getMenu";

        String response = coreobserverObj.makeAuthenticatedRequest(otLPEndPoint, "".getBytes(), "");
        return response;
    }

    @ObserveDataVessel(contextName = "Pizza Service | justToppingMenu", transactionIdString = "x-transaction-id")
    public String getToppingMenu(){
        String otLPEndPoint = "http://localhost:8503/demo/topping/getMenu";

        String response = coreobserverObj.makeAuthenticatedRequest(otLPEndPoint, "".getBytes(), "");
        return response;
    }
}
