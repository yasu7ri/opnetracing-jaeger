package com.example.spring_jaeger_main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/main")
public class MainController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public String main(@RequestParam String str) {
        ResponseEntity<String> FormatResponse = restTemplate.getForEntity(
                "http://localhost:8081/formatter?str={str}",
                String.class,
                str);
        String formatString = FormatResponse.getBody();

        ResponseEntity<String> publishResponse = restTemplate.getForEntity(
                "http://localhost:8082/publisher?str={str}",
                String.class,
                formatString);
        return publishResponse.getBody();
    }
}
