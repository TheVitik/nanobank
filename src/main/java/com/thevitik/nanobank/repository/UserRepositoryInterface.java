package com.thevitik.nanobank.repository;

import com.thevitik.nanobank.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepositoryInterface {
    int create(User user) throws SQLException;

    void delete(int id) throws SQLException;

    User findById(int id) throws SQLException;

    User findByPhone(String phone) throws SQLException;

    List<User> getUsers() throws SQLException;

    void update(User user) throws SQLException;
}
