package com.connext.loadblancing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {

  @LoadBalanced
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}

@RefreshScope
@RestController
class getMessage {

  @Autowired RestTemplate restTemplate;

  @Value("${message:Hello default}")
  private String message;

  @RequestMapping("/hi")
  public String hi(@RequestParam(value = "name", defaultValue = "Artaban") String name) {
    String greeting = this.restTemplate.getForObject("http://a-bootiful-client/", String.class);
    return String.format("%s:%s, %s!", greeting,this.message, name);
  }
}
