package com.example.demo.login.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * HomeController
 */
@Controller
public class HomeController {

     @Autowired
      UserService userService; 

     private Map<String, String> radioMarriage;

     // ラジオボタン初期化メソッド
     private Map<String, String> initRadioMarrige() {

        final Map<String, String> radio = new LinkedHashMap<>();

        radio.put("既婚", "true");
        radio.put("未婚", "false");

        return radio;
    }

     // ホーム画面用GETメソッド    
     @GetMapping("/home")
     public String getHome(final Model model) {

          model.addAttribute("contents", "login/home :: home_contents");

          return "login/homeLayout";
     }

     // ユーザ一覧画面のGETメソッド
     @GetMapping("/userList")
     public String getUserList(final Model model) {

          model.addAttribute("contents", "login/userList :: userList_contents");

          // Modelにユーザ一覧を登録
          final List<User> userList = userService.selectMany();
          model.addAttribute("userList", userList);

          // データ件数を取得
          final int count = userService.count();
          model.addAttribute("userListCount", count);

          return "login/homeLayout";
     }

     // ユーザ詳細画面のGETメソッド
     @GetMapping("/userDetail/{id:.+}")
     public String getUserDetail(@ModelAttribute final SignupForm form, final Model model, @PathVariable("id") final String userId) {

          System.out.println("userId = " + userId);          

          model.addAttribute("contents", "login/userDetail :: userDetail_contents");

          radioMarriage = initRadioMarrige();

          model.addAttribute("radioMarriage", radioMarriage);

          if (!StringUtils.isEmpty(userId)) {
               
               // ユーザ情報取得
               final User user = userService.selectOne(userId);

               // 取得したユーザ情報をフォームクラスに設定
               form.setUserId(user.getUserId());
               form.setUserName(user.getUserName());
               form.setAge(user.getAge());
               form.setBirthday(user.getBirthday());
               form.setMarriage(user.isMarriage());
          }
          
          return "login/homeLayout";
     }

     // ユーザー更新用メソッド
     @PostMapping(value = "/userDetail", params = "update")
     public String postUserDetailUpdate(@ModelAttribute final SignupForm form, final Model model) {
          
          System.out.println("更新ボタン処理");

          final User user = new User();

          // フォームクラスをUserクラスに変換
          user.setUserId(form.getUserId());
          user.setPassword(form.getPassword());
          user.setUserName(form.getUserName());
          user.setBirthday(form.getBirthday());
          user.setAge(form.getAge());
          user.setMarriage(form.isMarriage());

          try {
               // 更新処理
               final boolean result = userService.updateOne(user);

               if (result == true) {
                    model.addAttribute("result", "更新成功");
               } else {
                    model.addAttribute("result", "更新失敗");
               }

          } catch(DataAccessException e) {
               model.addAttribute("result", "更新失敗(トランザクションテスト");
          }

          // ユーザ一覧画面を表示
          return getUserList(model);
     }

     // ユーザー削除処理
     @PostMapping(value = "/userDetail", params = "delete")
     public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {

          System.out.println("削除ボタン処理");

          // 削除実行
          boolean result = userService.deleteOne(form.getUserId());

          if (result == true) {
               model.addAttribute("result", "削除成功");
          } else {
               model.addAttribute("result", "削除失敗");
          }

          return getUserList(model);
     } 

     // ユーザ一覧のCSV出力メソッド
     @GetMapping("/userList/csv")
     public ResponseEntity<byte[]> getUserListCsv(final Model model) {

          userService.userCsvOut();

          byte[] bytes = null;

          try {
               bytes = userService.getFile("sample.csv");
          } catch (IOException e) {
               e.printStackTrace();
          }

          HttpHeaders header = new HttpHeaders();
          header.add("Content-Type", "text/csv; charset=UTF-8");
          header.setContentDispositionFormData("filename", "sample.csv");

          return new ResponseEntity<>(bytes, header, HttpStatus.OK);
     }

     // ログアウト用POSTメソッド
     @PostMapping("/logout")
     public String postLogout() {
          
          return "redirect:/login";
     }
}