package com.example.demo.tyrSpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String getHello() {
        return "hello";
    }
}