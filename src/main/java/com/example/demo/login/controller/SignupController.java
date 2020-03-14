package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * SignupController
 */
@Controller
public class SignupController {

    private Map<String, String> radioMarriage;

    private Map<String, String> initRadioMarrige() {

        Map<String, String> radio = new LinkedHashMap<>();

        radio.put("既婚", "false");
        radio.put("未婚", "true");

        return radio;
    }
    
    /**
     * ユーザ登録画面のGET用コントローラ
     */
    @GetMapping("/signup")
    public String getSignUp(Model model) {

        // ラジオボタンの初期化メソッド呼び出し
        radioMarriage = initRadioMarrige();

        // ラジオボタン用のMapをModelに登録
        model.addAttribute("radioMarriage", radioMarriage);

        return "login/signup";
    }

    /**
     * ユーザー登録画面のPOSTコントローラ
     */
    public String postSignUp(Model model) {
        
        return "redirect:/login";

    }
}