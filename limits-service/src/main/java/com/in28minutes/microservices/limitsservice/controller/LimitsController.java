package com.in28minutes.microservices.limitsservice.controller;

import com.in28minutes.microservices.limitsservice.bean.LimitConfiguration;
import com.in28minutes.microservices.limitsservice.config.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limits")
public class LimitsController {

  @Autowired
  private Configuration configuration;

  @GetMapping("")
  public LimitConfiguration getLimits() {
    return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
  }

  @GetMapping("/fault-tolerance")
  @HystrixCommand(fallbackMethod = "fallbackFaultTolerance")
  public LimitConfiguration faultTolerance() {
    throw new RuntimeException("Not Available");
  }

  public LimitConfiguration fallbackFaultTolerance() {
    return new LimitConfiguration(999, 9);
  }

}
