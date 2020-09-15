package com.in28minutes.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.in28minutes.microservices.currencyconversionservice.bean.CurrencyConversionValue;
import com.in28minutes.microservices.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConversionController {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CurrencyExchangeServiceProxy proxy;

  @GetMapping("/from/{from}/to/{to}/{quantity}")
  public CurrencyConversionValue getCalculatedValue(@PathVariable String from, @PathVariable String to,
      @PathVariable BigDecimal quantity) {

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    ResponseEntity<CurrencyConversionValue> responseEntity = new RestTemplate().getForEntity(
        "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionValue.class, uriVariables);

    CurrencyConversionValue response = responseEntity.getBody();
    response.setTotalAmount(quantity.multiply(response.getConversionMultiple()));
    return response;
  }

  @GetMapping("/usingFeign/from/{from}/to/{to}/{quantity}")
  public CurrencyConversionValue getCalculatedValueUsingFeign(@PathVariable String from, @PathVariable String to,
      @PathVariable BigDecimal quantity) {

    CurrencyConversionValue response = proxy.retrieveExchangeValue(from, to);
    logger.info("{}", response);
    response.setQuantity(quantity);
    response.setTotalAmount(quantity.multiply(response.getConversionMultiple()));
    return response;
  }
}
