package com.in28minutes.microservices.currencyconversionservice.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CurrencyConversionValue {

  private Long id;
  private String from;
  private String to;
  private BigDecimal conversionMultiple;
  private BigDecimal quantity;
  private BigDecimal totalAmount;
  private int port;
}
