package com.in28minutes.microservices.currencyconversionservice.proxy;

import com.in28minutes.microservices.currencyconversionservice.bean.CurrencyConversionValue;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name = "currency-exchange-service", url = "localhost:8000/currency-exchange")
// @FeignClient(name = "currency-exchange-service")
@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

  // @GetMapping("/currency-exchange/from/{from}/to/{to}")
  @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
  public CurrencyConversionValue retrieveExchangeValue(@PathVariable("from") String from, @PathVariable String to);
}
