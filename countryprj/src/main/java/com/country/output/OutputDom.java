package com.country.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import io.micrometer.core.instrument.Meter;

import java.util.ArrayList;

@Getter()
public class OutputDom{

    @Setter  private String traceId;

    @Setter public String sys_transId;
    @Setter public String sys_Id;

   
    public Meter meter;

    @Setter private List<Spangroup> spangroups = new ArrayList<Spangroup>();

    public void addSpangroup(com.country.output.Spangroup spanGroup2) {
        if(spanGroup2 != null) {
            spangroups.add(spanGroup2);
        }
    }


}