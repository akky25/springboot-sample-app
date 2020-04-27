package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import com.example.demo.login.domain.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("UserDaoJdbcImp2")
public class UserDaoJdbcImp2 extends UserDaoJdbcImp {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public User selectOne(String userId) {

        // 1件取得用SQL
        String sql = "SELECT * FROM m_user WHERE user_id = ?";

        UserRowMapper rowMapper = new UserRowMapper();
        
        return jdbc.queryForObject(sql, rowMapper, userId);
    }
    
    @Override
    public List<User> selectMany() {

        String sql = "SELECT * FROM m_user";

        UserRowMapper rowMapper = new UserRowMapper();

        return jdbc.query(sql, rowMapper); 
    }
}