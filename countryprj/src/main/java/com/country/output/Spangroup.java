package com.country.output;

import lombok.Getter;
import lombok.Setter;

@Getter()
public class Spangroup {
    @Setter public String traceId;
    @Setter public String spanId;
    @Setter public String spanName;
}
