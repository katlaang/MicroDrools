package com.example.ratingsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RatingsDataServiceApplication {

//     KieSession kieSession = new DroolsRuleConfig().initialize();

    public static void main(String[] args) {
        SpringApplication.run(RatingsDataServiceApplication.class, args);
    }


}
