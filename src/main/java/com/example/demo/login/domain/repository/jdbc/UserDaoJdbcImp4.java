package com.example.demo.login.domain.repository.jdbc;

import java.util.List;

import com.example.demo.login.domain.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("UserDaoJdbcImp4")
public class UserDaoJdbcImp4 extends UserDaoJdbcImp {
    
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<User> selectMany() {

        //M_USERテーブルのデータを全件取得するSQL
        String sql = "SELECT * FROM m_user";

        //ResultSetExtractorの生成
        UserResultSetExtractor extractor = new UserResultSetExtractor();

        //SQL実行
        return jdbc.query(sql, extractor);
    }
}