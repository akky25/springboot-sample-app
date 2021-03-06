package com.example.demo.login.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * LoginController
 */
@Controller
public class LoginController {

    // ログイン画面のGETコントローラ
    @GetMapping(value="/login")
    public String getLogin(Model model) {
        return "login/login";
    }
    
    // ログイン画面のPOSTコントローラ
    @PostMapping(value="/login")
    public String postLogin(Model model) {
        return "redirect:/home";
    }

    // testコントローラ
    @GetMapping(value="/test")
    public String getTest(Model model) {

        List<String> lst = new ArrayList<>();
        if (lst.isEmpty()) {
            System.out.println("nulldayo");
        }

        return "login/login";
    }
    
}