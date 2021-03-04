package com.breader.microservices.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Limits {
    private int min;
    private int max;
}
