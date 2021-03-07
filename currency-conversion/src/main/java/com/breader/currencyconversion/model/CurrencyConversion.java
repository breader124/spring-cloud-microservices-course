package com.breader.currencyconversion.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversion {
    private Long id;
    private String from;
    private String to;
    private Double quantity;
    private Double totalCalculatedAmount;
    private Double conversionMultiple;
    private String environment;
}
