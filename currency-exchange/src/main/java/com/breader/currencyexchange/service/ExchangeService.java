package com.breader.currencyexchange.service;

import com.breader.currencyexchange.model.Exchange;
import com.breader.currencyexchange.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    public Optional<Exchange> getExchange(String from, String to) {
        return exchangeRepository.findByFromAndTo(from, to);
    }
}
