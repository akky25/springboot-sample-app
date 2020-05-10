package com.example.demo.login.controller;

import java.util.List;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.RestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    @Qualifier("RestServiceMybatisImpl")
    RestService service;

    // ユーザ全件取得
    @GetMapping("/rest/get")
    public List<User> getUserMany() {
        return service.selectMany();
    }

    // ユーザ１件取得
    @GetMapping("/rest/get/{id:.+}")
    public User getUserOneUser(@PathVariable("id") String userId) {
        return service.selectOne(userId);
    }

    // １件登録
    @PostMapping("/rest/insert")
    public String postUserOne(@RequestBody User user) {

        // １件登録
        return resultCheck(service.insert(user));
        
    }

    @PutMapping("/rest/update")
    public String putUserOne(@RequestBody User user) {
        
        //１件更新
        return resultCheck(service.update(user));

    }

    // 1件削除
    @DeleteMapping("/rest/delete/{id:.+}")
    public String deleteUserOne(@PathVariable("id") String userId) {

        // ユーザを１件削除
        return resultCheck(service.delete(userId));

    }

    private String resultCheck(boolean result) {

        if (result == true) {
            return "\"result\":\"ok\"";
        } else {
            return "\"result\":\"error\"";
        }
    }
}