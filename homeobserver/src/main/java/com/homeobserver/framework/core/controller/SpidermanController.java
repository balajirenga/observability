
package com.homeobserver.framework.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.homeobserver.framework.core.aop.LogInfo;
import com.homeobserver.framework.core.aspect.EnableHomeObserver;
import com.homeobserver.framework.core.dto.OutputResponseInfo;
import com.homeobserver.framework.core.myaspect.BalajiAspect;

@RestController
class SpidermanController {

    @Autowired
    DoSomething doSomethingObject;

    //@EnableHomeObserver(contextName = "SpidermanController")
    @GetMapping("/spiderman")
    @ResponseStatus(HttpStatus.OK)
    @BalajiAspect()
    public String doSpider() {
        System.out.println("at the spider method..");
        doSomethingObject.doSomething();
        return "Done";
    }

    @GetMapping("/justReceiveData")
    @ResponseStatus(HttpStatus.OK)
    public String doReceiveData(){
        System.out.println("at the receive data method..");
        return "Done";
    }


}