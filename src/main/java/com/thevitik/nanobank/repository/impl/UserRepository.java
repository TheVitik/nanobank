package com.thevitik.nanobank.repository.impl;

import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.UserRepositoryInterface;
import com.thevitik.nanobank.service.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserRepositoryInterface {

    /**
     * Create a new user
     */
    @Override
    public int create(User user) throws SQLException {
        String query = "INSERT INTO users VALUES(null,?,?,?,?,?)";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole());
            return ps.executeUpdate();
        }
    }

    /**
     * Delete a user
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Find user by id
     */
    @Override
    public User findById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        }
        return null;
    }

    @Override
    public User findByPhone(String phone) throws SQLException {
        String query = "SELECT * FROM users WHERE phone=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() throws SQLException {
        String query = "SELECT * FROM users ORDER BY id DESC";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
            return users;
        }
    }

    /**
     * User can update his password only
     */
    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE users SET password=?, role=? WHERE id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getRole());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new User().setId(rs.getInt("id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setPhone(rs.getString("phone"))
                .setPassword(rs.getString("password"))
                .setRole(rs.getInt("role"));
    }
}
