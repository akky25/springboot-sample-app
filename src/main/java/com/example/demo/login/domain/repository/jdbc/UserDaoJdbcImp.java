package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 * UserDaoJdbcImp
 */
@Repository("UserDaoJdbcImp")
public class UserDaoJdbcImp implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 件数取得メソッド
    @Override
    public int count() throws DataAccessException {
        
        // 全件取得
        int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);

        return count;
    }

    // 1件INSERTメソッド
    @Override
    public int insertOne(User user) throws DataAccessException {

        // パスワード暗号化
        String password = passwordEncoder.encode(user.getPassword());

        int rowNumber = jdbc.update("INSERT INTO m_user(user_id,"
            + " password,"
            + " user_name,"
            + " birthday,"
            + " age,"
            + " marriage,"
            + " role)"
            + " VALUES(?,?,?,?,?,?,?)"
            , user.getUserId()
            , password
            , user.getUserName()
            , user.getBirthday()
            , user.getAge()
            , user.isMarriage()
            , user.getRole());
        
        return rowNumber;
    }

    // 1件取得メソッド
    @Override
    public User selectOne(String userId) throws DataAccessException {

        // ユーザ取得
        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user WHERE user_id = ?", userId);

        // 返却用変数
        User user = new User();

        // 取得したデータを格納
        user.setUserId((String)map.get("user_id"));
        user.setPassword((String)map.get("password"));
        user.setUserName((String)map.get("user_name"));
        user.setBirthday((Date)map.get("birthday"));
        user.setAge((Integer)map.get("age"));
        user.setMarriage((Boolean)map.get("marriage"));

        return user;
    }

    // 全件取得
    @Override
    public List<User> selectMany() throws DataAccessException {
        
        // 全件取得
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");

        // 返却用変数
        List<User> userList = new ArrayList<>();

        for(Map<String, Object> map: getList) {

            User user = new User();

            // 取得したデータを格納
            user.setUserId((String)map.get("user_id"));
            user.setPassword((String)map.get("password"));
            user.setUserName((String)map.get("user_name"));
            user.setBirthday((Date)map.get("birthday"));
            user.setAge((Integer)map.get("age"));
            user.setMarriage((Boolean)map.get("marriage"));

            // 返却値に設定
            userList.add(user); 
        }

        return userList;
    }

    // 1件更新メソッド
    @Override
    public int updateOne(User user) throws DataAccessException {

        String password = passwordEncoder.encode(user.getPassword());

        int rowNumber = jdbc.update("UPDATE M_USER"
        + " SET"
        + " password = ?,"
        + " user_name = ?,"
        + " birthday = ?,"
        + " age = ?,"
        + " marriage = ?"
        + " WHERE user_id = ?",
        password,
        user.getUserName(),
        user.getBirthday(),
        user.getAge(),
        user.isMarriage(),
        user.getUserId());

        // if (rowNumber > 0) {
        //     throw new DataAccessException("トランザクションテスト") {

        //         /**
        //          *
        //          */
        //         private static final long serialVersionUID = 6616557945082519941L;
        //     };
        // }

        return rowNumber;
    }

    // 1件削除メソッド
    @Override
    public int deleteOne(String userId) throws DataAccessException {
        int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);
        return rowNumber;
    }

    // CSV出力メソッド
    @Override
    public void userCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM m_user";

        UserRowCallbackHandler handler = new UserRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}