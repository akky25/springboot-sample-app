package com.example.demo.login.domain.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.login.domain.model.User;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>>{
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException{

        List<User> userList = new ArrayList<>();

        while(rs.next()) {

            // List
            User user = new User();

            //取得したレコードをUserインスタンスにセット
            user.setUserId(rs.getString("user_id"));
            user.setPassword(rs.getString("password"));
            user.setUserName(rs.getString("user_name"));
            user.setBirthday(rs.getDate("birthday"));
            user.setAge(rs.getInt("age"));
            user.setMarriage(rs.getBoolean("marriage"));
            user.setRole(rs.getString("role"));

            userList.add(user);
        }

        if (userList.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return userList;
    }
}