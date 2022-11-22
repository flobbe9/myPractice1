package com.example.myPractice1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ThymeleafController {
    
    @GetMapping("/test")
    public String getTest() {

        return "test";
    }
}