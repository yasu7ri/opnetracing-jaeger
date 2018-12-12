package com.example.spring_jaeger_formatter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/formatter")
public class FormatController {
    @RequestMapping(method = RequestMethod.GET)
    public String format(@RequestParam String str) {
        return String.format("Hello, %s!", str);
    }
}
