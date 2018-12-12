package com.example.spring_jaeger_publisher;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publisher")
public class PublishController {
    @RequestMapping(method = RequestMethod.GET)
    public String publish(String str) {
        System.out.println(str);
        return "published:" + str;
    }
}
