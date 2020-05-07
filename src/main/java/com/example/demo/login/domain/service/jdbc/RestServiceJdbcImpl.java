package com.example.demo.login.domain.service.jdbc;

import java.util.List;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import com.example.demo.login.domain.service.RestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RestServiceJdbcImpl implements RestService {

    @Autowired
    @Qualifier("UserDaoJdbcImp")
    UserDao dao;

    // １件登録
    @Override
    public boolean insert(User user) {

        return resultCheck(dao.insertOne(user));
    }

    // １件取得
    @Override
    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    // 全件取得
    @Override
    public List<User> selectMany() {
        return dao.selectMany();
    }

    // １件更新
    @Override
    public boolean update(User user) {

        return resultCheck(dao.updateOne(user));
    }

    // 一件削除
    @Override
    public boolean delete(String userId) {

        return resultCheck(dao.deleteOne(userId));
    }

    private boolean resultCheck(int result) {

        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }
}