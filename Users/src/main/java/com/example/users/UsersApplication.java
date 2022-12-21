package com.example.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@SpringBootApplication
public class UsersApplication {

    @Bean
    public RestTemplate createRestTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

}
