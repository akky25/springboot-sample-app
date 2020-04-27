package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService
 */
@Transactional
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoJdbcImp2")
    UserDao dao;
    
    // insertメソッド
    public boolean insert(User user) {
        
        // insert実行
        int rowNumber = dao.insertOne(user);

        boolean result = false;

        if (rowNumber > 0) {
            // insert成功
            result = true;
        }

        return result;
    }

    // カウント用メソッド
    public int count() {
        return dao.count();
    }

    //  全件取得メソッド
    public List<User> selectMany() {
        return dao.selectMany();
    }

    // 1件取得メソッド
    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    // 1件更新メソッド
    public boolean updateOne(User user) {
        
        // 1件更新
        int rowNumber = dao.updateOne(user);

        // 判定用変数
        boolean result = false;

        // 更新判定
        if (rowNumber > 0) {
            // update成功
            result = true;
        } 

        return result;
    }

    // 1件削除メソッド
    public boolean deleteOne(String userId) {
        
        // 1件削除
        int rowNumber = dao.deleteOne(userId);

        // 判定用変数
        boolean result = false;

        // 更新判定
        if (rowNumber > 0) {
            // delete成功
            result = true;
        } 

        return result;
    }

    public void userCsvOut() throws DataAccessException{
        dao.userCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException{
        
        // ファイルシステムの取得
        FileSystem fs = FileSystems.getDefault();

        // ファイル取得
        Path p = fs.getPath(fileName);

        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}