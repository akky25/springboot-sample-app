package com.example.demo.login.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.SignupForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String getSignUp(@ModelAttribute SignupForm form, Model model) {

        // ラジオボタンの初期化メソッド呼び出し
        radioMarriage = initRadioMarrige();

        // ラジオボタン用のMapをModelに登録
        model.addAttribute("radioMarriage", radioMarriage);

        return "login/signup";
    }

    /**
     * ユーザー登録画面のPOSTコントローラ
     */
    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return getSignUp(form, model);
        }

        System.out.println(form);

        return "redirect:/login";
    }
}