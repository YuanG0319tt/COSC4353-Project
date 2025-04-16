package com.example.volunteerMatching.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home.html";
    }
}