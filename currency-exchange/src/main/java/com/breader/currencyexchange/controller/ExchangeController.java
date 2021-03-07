package com.breader.currencyexchange.controller;

import com.breader.currencyexchange.exception.ExchangeNotFoundException;
import com.breader.currencyexchange.model.Exchange;
import com.breader.currencyexchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    private final Environment environment;
    private final Random r = new Random();

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public Exchange getCurrencyExchangeInfo(@PathVariable String from, @PathVariable String to) throws ExchangeNotFoundException {
        return exchangeService.getExchange(from, to)
                .map(exchange -> {
                    exchange.setEnvironment(environment.getProperty("local.server.port"));
                    return exchange;
                })
                .orElseThrow(ExchangeNotFoundException::new);
    }
}
