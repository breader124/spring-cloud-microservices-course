package com.breader.currencyconversion.controller;

import com.breader.currencyconversion.model.CurrencyConversion;
import com.breader.currencyconversion.proxy.CurrencyExchangeProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final CurrencyExchangeProxy proxy;
    private final Environment environment;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyExchangeInfo(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable Double quantity) {

        Map<String, String> variableMap = new HashMap<>();
        variableMap.put("from", from);
        variableMap.put("to", to);

        String requestUri = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
        ResponseEntity<CurrencyConversion> responseEntity =
                restTemplate.getForEntity(requestUri, CurrencyConversion.class, variableMap);
        CurrencyConversion response = responseEntity.getBody();

        Double calculatedAmount = quantity * response.getConversionMultiple();
        response.setQuantity(quantity);
        response.setTotalCalculatedAmount(calculatedAmount);
        response.setEnvironment(environment.getProperty("local.server.port"));

        return response;
    }

    @GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion getCurrencyExchangeInfoWithFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable Double quantity) {

        CurrencyConversion response = proxy.retrieveExchangeValue(from, to);

        Double calculatedAmount = quantity * response.getConversionMultiple();
        response.setQuantity(quantity);
        response.setTotalCalculatedAmount(calculatedAmount);

        String env = response.getEnvironment() + " " + environment.getProperty("local.server.port");
        response.setEnvironment(env + " feign");

        return response;
    }
}
